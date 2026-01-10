package com.example.pocket_road_ui.ui.screens.capture

import android.net.Uri

sealed interface CaptureUiEvent {
    // Camera Actions
    data class OnPhotoCaptured(val uri: Uri) : CaptureUiEvent
    data class OnRemovePhoto(val uri: Uri) : CaptureUiEvent
    data object OnToggleFlash : CaptureUiEvent

    // Form Actions
    data class OnBrandChanged(val value: String) : CaptureUiEvent
    data class OnModelChanged(val value: String) : CaptureUiEvent
    data class OnYearChanged(val value: String) : CaptureUiEvent
    data class OnTypeChanged(val value: String) : CaptureUiEvent
    data object OnContinueToReview : CaptureUiEvent
    data object OnSubmitAnalysis : CaptureUiEvent
    data object OnBackToCamera : CaptureUiEvent
    data object OnSendForManualReview : CaptureUiEvent
    data object OnGoToGarage : CaptureUiEvent

    // Settings Events
    data class OnExposureChanged(val value: Float) : CaptureUiEvent
    data object OnToggleGrid : CaptureUiEvent
    data object OnToggleHdr : CaptureUiEvent
    data object OnToggleSmartContrast : CaptureUiEvent
    data class OnAspectRatioChanged(val ratio: Int) : CaptureUiEvent
}