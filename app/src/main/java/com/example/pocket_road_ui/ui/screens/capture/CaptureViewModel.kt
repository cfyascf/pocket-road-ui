package com.example.pocket_road_ui.ui.screens.capture

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.currentComposer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocket_road_ui.data.interfaces.ICardexRepository
import com.example.pocket_road_ui.data.remote.dto.RegisterCarRequest
import com.example.pocket_road_ui.domain.enums.CaptureStep
import com.example.pocket_road_ui.ui.screens.cardex.CardexViewModel
import com.example.pocket_road_ui.utils.FileUtils.getFileFromUri
import com.example.pocket_road_ui.utils.FileUtils.getRequestBodyFromString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CaptureViewModel @Inject constructor(
    private val repository: ICardexRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CaptureUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffects = Channel<CaptureSideEffect>()
    val sideEffects = _sideEffects.receiveAsFlow()

    fun onEvent(event: CaptureUiEvent) {
        when (event) {
            is CaptureUiEvent.OnPhotoCaptured -> onPhotoCaptured(event.uri)
            is CaptureUiEvent.OnRemovePhoto -> onRemovePhoto(event.uri)
            is CaptureUiEvent.OnToggleFlash -> toggleFlash()
            is CaptureUiEvent.OnContinueToReview -> onContinueToReview()
            is CaptureUiEvent.OnBrandChanged -> _uiState.update { it.copy(hints = it.hints.copy(brand = event.value)) }
            is CaptureUiEvent.OnModelChanged -> _uiState.update { it.copy(hints = it.hints.copy(model = event.value)) }
            is CaptureUiEvent.OnYearChanged -> _uiState.update { it.copy(hints = it.hints.copy(year = event.value)) }
            is CaptureUiEvent.OnTypeChanged -> _uiState.update { it.copy(hints = it.hints.copy(type = event.value)) }
            is CaptureUiEvent.OnSubmitAnalysis -> onSubmitAnalysis()
            is CaptureUiEvent.OnBackToCamera -> onBackToCamera()
            is CaptureUiEvent.OnSendForManualReview -> onSendForManualReview()
            CaptureUiEvent.OnGoToGarage -> onGoToGarage()

            // Settings Handlers
            is CaptureUiEvent.OnExposureChanged -> _uiState.update { it.copy(exposureValue = event.value) }
            is CaptureUiEvent.OnToggleGrid -> _uiState.update { it.copy(isGridEnabled = !it.isGridEnabled) }
            is CaptureUiEvent.OnToggleHdr -> _uiState.update { it.copy(isHdrEnabled = !it.isHdrEnabled) }
            is CaptureUiEvent.OnToggleSmartContrast -> _uiState.update { it.copy(isSmartContrastEnabled = !it.isSmartContrastEnabled) }
            is CaptureUiEvent.OnAspectRatioChanged -> _uiState.update { it.copy(aspectRatio = event.ratio) }
        }
    }

    private fun onGoToGarage() {
        viewModelScope.launch {
            _sideEffects.send(CaptureSideEffect.NavigateToCardex)
        }
    }

    private fun onPhotoCaptured(uri: Uri) {
        val currentList = _uiState.value.capturedPhotos
        if (currentList.size < 6) {
            _uiState.update { it.copy(capturedPhotos = currentList + uri) }
        } else {
            sendEffect(CaptureSideEffect.ShowToast("Max 6 photos allowed"))
        }
    }

    private fun onRemovePhoto(uri: Uri) {
        _uiState.update { it.copy(capturedPhotos = it.capturedPhotos - uri) }
    }

    private fun toggleFlash() {
        _uiState.update { it.copy(isFlashOn = !it.isFlashOn) }
    }

    private fun onContinueToReview() {
        if (_uiState.value.capturedPhotos.isNotEmpty()) {
            _uiState.update { it.copy(currentStep = CaptureStep.REVIEW_AND_HINTS) }
        } else {
            sendEffect(CaptureSideEffect.ShowToast("Take at least 1 photo"))
        }
    }

    private fun onBackToCamera() {
        _uiState.update { it.copy(currentStep = CaptureStep.CAMERA) }
    }

    private fun onSubmitAnalysis() {
        viewModelScope.launch {
            _uiState.update { it.copy(currentStep = CaptureStep.PROCESSING) }

            val state = _uiState.value
            val result = repository.registerCar(RegisterCarRequest(
                photos = state.capturedPhotos,
                modelHint = state.hints.model,
                brandHint = state.hints.brand,
                yearHint = state.hints.year,
                typeHint = state.hints.type
            ))

            result.onSuccess { dto ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        currentStep = CaptureStep.RESULT_SUCCESS
                    )
                }
            }

            result.onFailure { error ->
                val errorMessage = parseErrorMessage(error)

                when (error) {
                    is HttpException -> {
                        val code = error.code()
                        Log.e(TAG, "API Error: $code")
                        when (code) {
                            409 -> {
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        currentStep = CaptureStep.RESULT_FAILURE
                                    )
                                }
                            }
                        }
                    }
                }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = errorMessage
                    )
                }
            }
        }
    }

    private fun parseErrorMessage(error: Throwable): String {
        return when (error) {
            is HttpException -> {
                val code = error.code()
                Log.e(TAG, "API Error: $code")
                when (code) {
                    401 -> "Session expired"
                    404 -> "Garage not found"
                    409 -> "Failed to recognize car"
                    500, 502, 503 -> "Server error. Try again later."
                    else -> "Network Error ($code)"
                }
            }
            is IOException -> "No internet connection"
            else -> {
                Log.e(TAG, "Unknown Error", error)
                error.message ?: "An unexpected error occurred"
            }
        }
    }

    private fun onSendForManualReview() {
        viewModelScope.launch {
            sendEffect(CaptureSideEffect.ShowToast("Sent to manual review"))
            delay(1000)
            sendEffect(CaptureSideEffect.NavigateToCardex)
        }
    }

    private fun updateStatus(msg: String) {
        _uiState.update { it.copy(processingMessage = msg) }
    }

    private fun sendEffect(effect: CaptureSideEffect) {
        viewModelScope.launch { _sideEffects.send(effect) }
    }

    companion object {
        private const val TAG = "CaptureViewModel"
    }
}