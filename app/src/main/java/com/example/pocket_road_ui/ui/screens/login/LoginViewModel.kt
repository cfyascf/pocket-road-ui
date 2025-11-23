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
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: IAuthRepository
) : ViewModel() {

    // State to control the UI
    var username by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var loginSuccess by mutableStateOf(false)
    var isRegistering by mutableStateOf(false)
    var notificationMessage by mutableStateOf<String?>(null)
    var showNotification by mutableStateOf(false)
    var notificationType by mutableStateOf(NotificationType.ERROR)

    fun onLoginClick(onNavigateToHomePage: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            showNotification = false

            val result = repository.login(username, password)

            result.onSuccess { response ->
                // Here you would save the token to SharedPreferences/DataStore
                isLoading = false
                loginSuccess = true
                notificationType = NotificationType.SUCCESS
                notificationMessage = "Login efetivado com sucesso!"
                showNotification = true
            }

            result.onFailure { error ->
                isLoading = false
                errorMessage = error.message ?: "Unknown error"
                notificationType = NotificationType.ERROR
                if (error is retrofit2.HttpException && error.code() == 400) {
                    notificationMessage = "Este usuário ou email já existe."
                } else {
                    notificationMessage = "Erro de conexão: ${error.message}"
                }

                showNotification = true
            }
        }

        onNavigateToHomePage()
    }

    fun onRegisterClick(onNavigateToHomePage: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            showNotification = false

            val result = repository.register(username, email, password)

            result.onSuccess { response ->
                // Here you would save the token to SharedPreferences/DataStore
                isLoading = false
                loginSuccess = true
                notificationType = NotificationType.SUCCESS
                notificationMessage = "Conta criada com sucesso!"
                showNotification = true

                delay(1000)
                onNavigateToHomePage()
            }

            result.onFailure { error ->
                isLoading = false
                errorMessage = error.message ?: "Unknown error"
                notificationType = NotificationType.ERROR
                if (error is retrofit2.HttpException && error.code() == 400) {
                    notificationMessage = "Este usuário ou email já existe."
                } else {
                    notificationMessage = "Erro de conexão: ${error.message}"
                }

                showNotification = true
            }
        }
    }

    fun dismissNotification() {
        showNotification = false
    }
}