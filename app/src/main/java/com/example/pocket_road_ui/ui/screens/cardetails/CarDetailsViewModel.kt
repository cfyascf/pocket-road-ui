package com.example.pocket_road_ui.ui.screens.cardetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocket_road_ui.data.interfaces.ICardexRepository
import com.example.pocket_road_ui.domain.mapper.toEntity
import com.example.pocket_road_ui.ui.screens.login.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CarDetailsViewModel @Inject constructor(
    private val repository: ICardexRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(CarDetailsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val carId = savedStateHandle.get<String>("carId")
        if (carId != null) {
            fetchDetailsData(carId)
        } else {
            _uiState.update { it.copy(errorMessage = "Invalid Car ID") }
        }
    }

    fun fetchDetailsData(carId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = repository.getCarDetails(carId)

            result.onSuccess { response ->
                val data = response.data
                if (data == null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Error."
                        )
                    }
                    return@launch
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        carDetails = data.toEntity()
                    )
                }
            }

            result.onFailure { error ->
                val errorMessage = parseErrorMessage(error)
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = errorMessage)
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
                    404 -> "Service not found"
                    500, 502, 503 -> "Server error. Please try again later."
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

    companion object {
        private const val TAG = "CarDetailsViewModel"
    }
}