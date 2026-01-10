package com.example.pocket_road_ui.ui.screens.capture

import android.Manifest
import android.content.pm.PackageManager
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pocket_road_ui.domain.enums.CaptureStep
import com.example.pocket_road_ui.ui.screens.capture.components.FailureView
import com.example.pocket_road_ui.ui.screens.capture.components.PermissionView
import com.example.pocket_road_ui.ui.screens.capture.components.ProcessingView
import com.example.pocket_road_ui.ui.screens.capture.components.ReviewFormView
import com.example.pocket_road_ui.ui.screens.capture.components.CameraView
import com.example.pocket_road_ui.ui.screens.capture.components.SuccessView

@Composable
fun CaptureScreen(
    viewModel: CaptureViewModel = hiltViewModel(),
    onNavigateToCardex: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // 2. Setup Permission Launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
        }
    )

    LaunchedEffect(Unit) {
        viewModel.sideEffects.collect { effect ->
            when (effect) {
                is CaptureSideEffect.NavigateToCardex -> onNavigateToCardex()
                is CaptureSideEffect.ShowToast -> { /* Show Toast */ }
            }
        }
    }

    if (!hasCameraPermission) {
        PermissionView(
            onRequestPermission = {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        )
    } else {
        CaptureContent(
            state = state,
            onEvent = viewModel::onEvent
        )
    }
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
                CaptureStep.RESULT_SUCCESS -> SuccessView(state = state, onEvent = onEvent)
                CaptureStep.RESULT_FAILURE -> FailureView(onEvent = onEvent)
            }
        }
    }
}

@Preview
@Composable
fun CaptureScreenPreview() {
    CaptureContent(
        state = CaptureUiState(),
        onEvent = {} // Empty lambda that does nothing
    )
}

@Preview(name = "Review Step")
@Composable
fun CaptureReviewPreview() {
    CaptureContent(
        state = CaptureUiState(),
        onEvent = {}
    )
}