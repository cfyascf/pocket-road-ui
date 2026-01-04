package com.example.pocket_road_ui.ui.screens.cardex

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocket_road_ui.data.interfaces.ICardexRepository
import com.example.pocket_road_ui.data.local.SessionManager
import com.example.pocket_road_ui.domain.mapper.toEntity
import com.example.pocket_road_ui.domain.models.CardexKpis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

sealed interface CardexSideEffect {
    data class NavigateToCarDetails(val carId: String) : CardexSideEffect
    data object NavigateToLogin : CardexSideEffect
}

@HiltViewModel
class CardexViewModel @Inject constructor(
    private val repository: ICardexRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(CardexUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffects = Channel<CardexSideEffect>()
    val sideEffects = _sideEffects.receiveAsFlow()


    init {
        fetchGarageData()
    }

    fun onCarClicked(carId: String) {
        viewModelScope.launch {
            _sideEffects.send(CardexSideEffect.NavigateToCarDetails(carId))
        }
    }

    fun fetchGarageData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val userId = sessionManager.userId.first()
            if (userId == null) {
                handleSessionError()
                return@launch
            }

            val result = repository.getCardex(userId)

            result.onSuccess { dto ->
                val data = dto.data
                if(data == null) {
                    Log.e(TAG, "Server returned empty data from a successful request.")

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Server response error.")
                    }

                    return@launch
                }

                val cars = data.cars.map { it.toEntity() }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        cars = cars,
                        kpis = CardexKpis(
                            data.capturedCount,
                            data.garageValue,
                            data.ranking)
                    )
                }
            }

            result.onFailure { error ->
                val errorMessage = parseErrorMessage(error)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = errorMessage
                    )
                }
            }
        }
    }

    private suspend fun handleSessionError() {
        Log.e(TAG, "User ID not found in session.")
        _uiState.update {
            it.copy(
                isLoading = false,
                errorMessage = "Session expired. Please login again."
            )
        }
        _sideEffects.send(CardexSideEffect.NavigateToLogin)
    }

    private fun parseErrorMessage(error: Throwable): String {
        return when (error) {
            is HttpException -> {
                val code = error.code()
                Log.e(TAG, "API Error: $code")
                when (code) {
                    401 -> "Session expired"
                    404 -> "Garage not found"
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

    companion object {
        private const val TAG = "CardexViewModel"
    }
}