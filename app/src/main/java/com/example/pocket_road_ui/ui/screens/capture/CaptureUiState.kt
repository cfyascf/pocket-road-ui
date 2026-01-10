package com.example.pocket_road_ui.ui.screens.capture

import android.net.Uri
import androidx.camera.core.AspectRatio
import com.example.pocket_road_ui.domain.enums.CaptureStep
import com.example.pocket_road_ui.domain.models.Car
import com.example.pocket_road_ui.ui.screens.capture.components.RecognizedCar

data class CarHints(
    val brand: String = "",
    val model: String = "",
    val year: String = "",
    val type: String = "",
)

data class CaptureUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,

    val currentStep: CaptureStep = CaptureStep.CAMERA,
    val capturedPhotos: List<Uri> = emptyList(),
    val hints: CarHints = CarHints(),
    val isFlashOn: Boolean = false,
    val processingMessage: String = "Initializing AI...",
    val recognizedCar: RecognizedCar? = null,

    // Camera Settings
    val exposureValue: Float = 0f,
    val isGridEnabled: Boolean = true,
    val isHdrEnabled: Boolean = false, // Will map to CaptureMode.MAXIMIZE_QUALITY
    val isSmartContrastEnabled: Boolean = false,
    val aspectRatio: Int = AspectRatio.RATIO_4_3 // Default standard photo ratio
)