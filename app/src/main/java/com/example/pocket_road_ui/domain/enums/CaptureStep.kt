package com.example.pocket_road_ui.domain.enums

// The 3 stages of this screen
enum class CaptureStep {
    CAMERA,
    REVIEW_AND_HINTS,
    PROCESSING,
    RESULT_SUCCESS, // Success redirects to Cardex, Failure shows a specific UI
    RESULT_FAILURE // Success redirects to Cardex, Failure shows a specific UI
}