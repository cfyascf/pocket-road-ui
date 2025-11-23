package com.example.pocket_road_ui.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocket_road_ui.data.repository.IAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: IAuthRepository
) : ViewModel() {

    // State to control the UI
    var username by mutableStateOf("")
    var password by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var loginSuccess by mutableStateOf(false)
    var isRegistering by mutableStateOf(false)

    fun onLoginClick() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            val result = repository.login(username, password)

            result.onSuccess { response ->
                // Here you would save the token to SharedPreferences/DataStore
                isLoading = false
                loginSuccess = true
                println("Token: ${response.data}")
            }

            result.onFailure { error ->
                isLoading = false
                errorMessage = error.message ?: "Unknown error"
            }
        }
    }
}