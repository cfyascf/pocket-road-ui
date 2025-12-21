package com.example.pocket_road_ui.ui.screens.login

import com.example.pocket_road_ui.ui.components.NotificationType

data class LoginUiState(
    // form fields
    val username: String = "",
    val email: String = "",
    val password: String = "",

    // screen state
    val isLoading: Boolean = false,
    val isRegistering: Boolean = false,
    val loginSuccess: Boolean = false,

    val notification: NotificationData? = null
)
data class NotificationData(
    val message: String?,
    val type: NotificationType
)