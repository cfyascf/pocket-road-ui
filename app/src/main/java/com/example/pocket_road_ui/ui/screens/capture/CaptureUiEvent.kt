package com.example.pocket_road_ui.ui.screens.capture

import android.net.Uri

sealed interface CaptureUiEvent {
    // Camera Actions
    data class OnPhotoCaptured(val uri: Uri) : CaptureUiEvent
    data class OnRemovePhoto(val uri: Uri) : CaptureUiEvent
    data object OnToggleFlash : CaptureUiEvent
    data object OnContinueToReview : CaptureUiEvent

    // Form Actions
    data class OnBrandChanged(val value: String) : CaptureUiEvent
    data class OnModelChanged(val value: String) : CaptureUiEvent
    data class OnYearChanged(val value: String) : CaptureUiEvent
    data object OnSubmitAnalysis : CaptureUiEvent

    // Navigation/Misc
    data object OnBackToCamera : CaptureUiEvent
    data object OnSendForManualReview : CaptureUiEvent
}