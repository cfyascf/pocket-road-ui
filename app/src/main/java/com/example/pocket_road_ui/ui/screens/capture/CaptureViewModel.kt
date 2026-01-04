package com.example.pocket_road_ui.ui.screens.capture

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocket_road_ui.domain.enums.CaptureStep
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface CaptureSideEffect {
    data object NavigateToCardex : CaptureSideEffect
    data class ShowToast(val message: String) : CaptureSideEffect
}

@HiltViewModel
class CaptureViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(CaptureUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffects = Channel<CaptureSideEffect>()
    val sideEffects = _sideEffects.receiveAsFlow()

    fun onEvent(event: CaptureUiEvent) {
        when (event) {
            is CaptureUiEvent.OnPhotoCaptured -> onPhotoCaptured(event.uri)
            is CaptureUiEvent.OnRemovePhoto -> onRemovePhoto(event.uri)
            CaptureUiEvent.OnToggleFlash -> toggleFlash()
            CaptureUiEvent.OnContinueToReview -> onContinueToReview()
            is CaptureUiEvent.OnBrandChanged -> _uiState.update { it.copy(hints = it.hints.copy(brand = event.value)) }
            is CaptureUiEvent.OnModelChanged -> _uiState.update { it.copy(hints = it.hints.copy(model = event.value)) }
            is CaptureUiEvent.OnYearChanged -> _uiState.update { it.copy(hints = it.hints.copy(year = event.value)) }
            CaptureUiEvent.OnSubmitAnalysis -> onSubmitAnalysis()
            CaptureUiEvent.OnBackToCamera -> onBackToCamera()
            CaptureUiEvent.OnSendForManualReview -> onSendForManualReview()
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

            // SIMULATE AI ANALYSIS (Replace this with real API call logic)
            updateStatus("Uploading images...")
            delay(1500)
            updateStatus("Analyzing geometry...")
            delay(1500)
            updateStatus("Matching with database...")
            delay(1500)

            // Randomly succeed or fail for demo purposes
            val success = true // Toggle this to test failure view

            if (success) {
                updateStatus("Car Identified!")
                delay(500)
                sendEffect(CaptureSideEffect.NavigateToCardex)
            } else {
                _uiState.update { it.copy(currentStep = CaptureStep.RESULT_FAILURE) }
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
}