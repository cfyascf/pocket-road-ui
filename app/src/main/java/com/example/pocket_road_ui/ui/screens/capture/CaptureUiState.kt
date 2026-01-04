package com.example.pocket_road_ui.ui.screens.capture

import android.net.Uri
import com.example.pocket_road_ui.domain.enums.CaptureStep

data class CarHints(
    val brand: String = "",
    val model: String = "",
    val year: String = ""
)

data class CaptureUiState(
    val currentStep: CaptureStep = CaptureStep.CAMERA,
    val capturedPhotos: List<Uri> = emptyList(),
    val hints: CarHints = CarHints(),
    val isFlashOn: Boolean = false,
    // For the "AI" Loading screen
    val processingMessage: String = "Initializing AI..."
)