package com.example.pocket_road_ui.ui.components.form

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType

data class InputData(
    var value: String,
    val onValueChange: (String) -> Unit,
    val label: String,
    val icon: ImageVector,
    val placeholder: String,
    val keyboardType: KeyboardType,
    val isPassword: Boolean
)