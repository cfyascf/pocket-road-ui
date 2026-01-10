package com.example.pocket_road_ui.ui.screens.capture

sealed interface CaptureSideEffect {
    data object NavigateToCardex : CaptureSideEffect
    data class ShowToast(val message: String) : CaptureSideEffect
}