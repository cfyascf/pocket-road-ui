package com.example.pocket_road_ui.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocket_road_ui.data.repository.IAuthRepository
import com.example.pocket_road_ui.ui.components.NotificationType
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: IAuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

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
        _uiState.update { it.copy(isRegistering = !it.isRegistering) }
    }

    fun onLoginClick(onNavigateToHomePage: () -> Unit) {
        viewModelScope.launch {

            _uiState.update {
                it.copy(isLoading = true, notification = null)
            }

            val currentState = _uiState.value
            val result = repository.login(currentState.username, currentState.password)

            result.onSuccess { response ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        loginSuccess = true,
                        notification = NotificationData(
                            response.message,
                            NotificationType.SUCCESS
                        )
                    )
                }

                delay(3000)
                onNavigateToHomePage()
            }

            result.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        loginSuccess = false,
                        notification = NotificationData(
                            error.message,
                            NotificationType.ERROR
                        )
                    )
                }
            }
        }
    }

    fun onRegisterClick(onNavigateToHomePage: () -> Unit) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, notification = null)
            }

            val currentState = _uiState.value
            val result = repository.register(currentState.username, currentState.email, currentState.password)

            result.onSuccess { response ->
                // Here you would save the token to SharedPreferences/DataStore
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        loginSuccess = true,
                        notification = NotificationData(
                            response.message,
                            NotificationType.SUCCESS
                        )
                    )
                }

                delay(1000)
                onNavigateToHomePage()
            }

            result.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        loginSuccess = false,
                        notification = NotificationData(
                            error.message,
                            NotificationType.ERROR
                        )
                    )
                }
            }
        }
    }

    fun dismissNotification() {
        _uiState.update { it.copy(notification = null) }
    }
}