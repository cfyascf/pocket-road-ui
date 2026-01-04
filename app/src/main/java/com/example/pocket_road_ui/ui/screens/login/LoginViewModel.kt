package com.example.pocket_road_ui.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocket_road_ui.data.interfaces.IAuthRepository
import com.example.pocket_road_ui.data.local.SessionManager
import com.example.pocket_road_ui.data.remote.dto.ApiResponse
import com.example.pocket_road_ui.ui.components.NotificationType
import com.example.pocket_road_ui.data.remote.dto.AuthResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import retrofit2.HttpException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface LoginSideEffect {
    data object NavigateToHome : LoginSideEffect
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: IAuthRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    // Channel for one-time events (Navigation)
    private val _sideEffects = Channel<LoginSideEffect>()
    val sideEffects = _sideEffects.receiveAsFlow()


    // User Input Handlers
    fun onUsernameChange(newValue: String) {
        _uiState.update { it.copy(username = newValue) }
    }

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue) }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue) }
    }

    fun toggleRegisterMode() {
        _uiState.update { it.copy(isRegistering = !it.isRegistering, notification = null) }
    }

    fun dismissNotification() {
        _uiState.update { it.copy(notification = null) }
    }


    // Auth Actions
    fun onLoginClick() {
        val state = _uiState.value
        performAuth { repository.login(state.username, state.password) }
    }

    fun onRegisterClick() {
        val state = _uiState.value
        performAuth { repository.register(state.username, state.email, state.password) }
    }

    private fun performAuth(authCall: suspend () -> Result<ApiResponse<AuthResponse?>>) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, notification = null) }

            val result = authCall()

            result.onSuccess { response ->
                val authData = response.data

                if (authData == null) {
                    Log.e(TAG, "Server returned empty data from a successful request.")

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            notification = NotificationData("Server response error.", NotificationType.ERROR)
                        )
                    }

                    return@onSuccess
                }

                sessionManager.saveAuthInfo(authData.token, authData.userId)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        loginSuccess = true,
                        notification = NotificationData("Login successful!", NotificationType.SUCCESS)
                    )
                }

                delay(500)
                _sideEffects.send(LoginSideEffect.NavigateToHome)
            }

            result.onFailure { error ->
                val message = parseErrorMessage(error)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        loginSuccess = false,
                        notification = NotificationData(message, NotificationType.ERROR)
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
                    401 -> "Invalid credentials"
                    404 -> "Service not found"
                    400 -> "User already exists"
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
        private const val TAG = "LoginViewModel"
    }
}