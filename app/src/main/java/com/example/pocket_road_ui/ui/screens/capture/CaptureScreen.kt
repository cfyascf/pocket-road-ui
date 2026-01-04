package com.example.pocket_road_ui.ui.screens.capture

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pocket_road_ui.domain.enums.CaptureStep
import com.example.pocket_road_ui.ui.screens.capture.components.FailureView
import com.example.pocket_road_ui.ui.screens.capture.components.ProcessingView
import com.example.pocket_road_ui.ui.screens.capture.components.ReviewFormView
import com.example.pocket_road_ui.ui.screens.capture.views.CameraView

@Composable
fun CaptureScreen(
    viewModel: CaptureViewModel = hiltViewModel(),
    onNavigateToCardex: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffects.collect { effect ->
            when (effect) {
                is CaptureSideEffect.NavigateToCardex -> onNavigateToCardex()
                is CaptureSideEffect.ShowToast -> { /* Show Toast */ }
            }
        }
    }

    var hasCameraPermission by remember { mutableStateOf(false) }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { hasCameraPermission = it }
    )

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    if (!hasCameraPermission) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Camera permission required", color = Color.White)
        }
        return
    }

    CaptureContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun CaptureContent(
    state: CaptureUiState,
    onEvent: (CaptureUiEvent) -> Unit
) {
    Scaffold(
        containerColor = Color.Black
    ) { padding ->
        AnimatedContent(
            targetState = state.currentStep,
            label = "Capture Flow",
            modifier = Modifier.padding(padding)
        ) { step ->
            when (step) {
                CaptureStep.CAMERA -> CameraView(state = state, onEvent = onEvent)
                CaptureStep.REVIEW_AND_HINTS -> ReviewFormView(state = state, onEvent = onEvent)
                CaptureStep.PROCESSING -> ProcessingView(state = state)
                CaptureStep.RESULT_FAILURE -> FailureView(onEvent = onEvent)
            }
        }
    }
}

@Preview
@Composable
fun CaptureScreenPreview() {
    CaptureContent(
        state = CaptureUiState(
            currentStep = CaptureStep.CAMERA,
            // You can fake other state here to test different looks
            // capturedPhotos = listOf(Uri.EMPTY)
        ),
        onEvent = {} // Empty lambda that does nothing
    )
}

@Preview(name = "Review Step")
@Composable
fun CaptureReviewPreview() {
    CaptureContent(
        state = CaptureUiState(currentStep = CaptureStep.REVIEW_AND_HINTS),
        onEvent = {}
    )
}