package com.example.pocket_road_ui.ui.screens.capture.components

import android.content.pm.PackageManager
import android.view.ViewGroup
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.AsyncImage
import com.example.pocket_road_ui.ui.screens.capture.CaptureUiEvent
import com.example.pocket_road_ui.ui.screens.capture.CaptureUiState
import com.example.pocket_road_ui.ui.theme.AppDimensions
import com.example.pocket_road_ui.utils.CameraUtils
import java.io.File
import java.util.concurrent.Executors

@Composable
fun CameraView(
    state: CaptureUiState,
    onEvent: (CaptureUiEvent) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val isPreview = LocalInspectionMode.current

    val cameraExecutor = remember(isPreview) {
        if (!isPreview) Executors.newSingleThreadExecutor() else null
    }

    var cameraProvider: ProcessCameraProvider? by remember { mutableStateOf(null) }
    var camera: Camera? by remember { mutableStateOf(null) }

    // Store previewView to reuse in re-binding
    var previewView: PreviewView? by remember { mutableStateOf(null) }
    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }

    var showSettings by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose {
            try { cameraProvider?.unbindAll() } catch (e: Exception) {}
            cameraExecutor?.shutdown()
        }
    }

    // Effect: Bind/Re-bind camera when Aspect Ratio or HDR changes
    // This is the SINGLE SOURCE OF TRUTH for binding logic.
    LaunchedEffect(state.aspectRatio, state.isHdrEnabled, cameraProvider, previewView) {
        val provider = cameraProvider
        val view = previewView
        if (provider != null && view != null) {
            try {
                // Must unbind before rebinding with new settings
                provider.unbindAll()

                // Apply Aspect Ratio Strategy
                val resolutionSelector = ResolutionSelector.Builder()
                    .setAspectRatioStrategy(AspectRatioStrategy(state.aspectRatio, AspectRatioStrategy.FALLBACK_RULE_AUTO))
                    .build()

                val preview = Preview.Builder()
                    .setResolutionSelector(resolutionSelector)
                    .build()
                    .also { it.setSurfaceProvider(view.surfaceProvider) }

                // Configure HDR (Quality) vs Speed
                val captureMode = if (state.isHdrEnabled)
                    ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
                else
                    ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY

                val newImageCapture = ImageCapture.Builder()
                    .setResolutionSelector(resolutionSelector)
                    .setCaptureMode(captureMode)
                    .build()

                imageCapture = newImageCapture // Update reference for Shutter Button

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                camera = provider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, newImageCapture)
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    // Effect: Update Exposure when slider changes
    LaunchedEffect(state.exposureValue, camera) {
        camera?.let { cam ->
            val exposureState = cam.cameraInfo.exposureState
            if (exposureState.isExposureCompensationSupported) {
                val range = exposureState.exposureCompensationRange
                val step = exposureState.exposureCompensationStep.toFloat()
                val targetIndex = (state.exposureValue / step).toInt()
                val clampedIndex = targetIndex.coerceIn(range.lower, range.upper)
                cam.cameraControl.setExposureCompensationIndex(clampedIndex)
            }
        }
    }

    // Effect: Toggle Flash (Torch Mode)
    LaunchedEffect(state.isFlashOn, camera) {
        camera?.let { cam ->
            if (cam.cameraInfo.hasFlashUnit()) {
                try {
                    cam.cameraControl.enableTorch(state.isFlashOn)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    val hasCameraHardware = remember(context) {
        context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {

        if (isPreview || !hasCameraHardware) {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Text("Camera Preview", color = Color.White)
            }
        } else {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    val view = PreviewView(ctx).apply {
                        scaleType = PreviewView.ScaleType.FILL_CENTER
                        implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }
                    previewView = view // Save reference

                    val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx.applicationContext)
                    cameraProviderFuture.addListener({
                        try {
                            cameraProvider = cameraProviderFuture.get()
                            // Note: We DO NOT bind here anymore.
                            // Setting 'cameraProvider' will trigger the LaunchedEffect above.
                        } catch (exc: Exception) { exc.printStackTrace() }
                    }, androidx.core.content.ContextCompat.getMainExecutor(ctx))
                    view
                }
            )
        }

        // Grid Overlay (Toggles visibility based on state)
        if (state.isGridEnabled) {
            Canvas(modifier = Modifier.fillMaxSize().zIndex(1f)) {
                val width = size.width
                val height = size.height
                val color = Color.White.copy(alpha = 0.5f)
                val strokeWidth = 1.dp.toPx()

                drawLine(color, Offset(width / 3, 0f), Offset(width / 3, height), strokeWidth)
                drawLine(color, Offset(2 * width / 3, 0f), Offset(2 * width / 3, height), strokeWidth)
                drawLine(color, Offset(0f, height / 3), Offset(width, height / 3), strokeWidth)
                drawLine(color, Offset(0f, 2 * height / 3), Offset(width, 2 * height / 3), strokeWidth)
            }
        }

        // UI Overlay
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = AppDimensions.statusBarTopPadding + 48.dp,
                    bottom = AppDimensions.navBarBottomPadding + 32.dp,
                    start = 24.dp,
                    end = 24.dp
                )
                .zIndex(2f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onEvent(CaptureUiEvent.OnToggleFlash) },
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Black.copy(alpha = 0.4f), CircleShape)
                        .border(1.dp, Color.White.copy(alpha = 0.1f), CircleShape)
                ) {
                    AnimatedContent(
                        targetState = state.isFlashOn,
                        label = "Flash",
                        transitionSpec = { scaleIn() togetherWith scaleOut() }
                    ) { isOn ->
                        Icon(
                            imageVector = if (isOn) Icons.Default.Bolt else Icons.Default.FlashOff,
                            contentDescription = "Flash",
                            tint = if (isOn) Color(0xFFFACC15) else Color.White
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(50))
                        .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(50))
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "${state.capturedPhotos.size}/6",
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                IconButton(
                    onClick = { showSettings = true },
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Black.copy(alpha = 0.4f), CircleShape)
                        .border(1.dp, Color.White.copy(alpha = 0.1f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = Color.White
                    )
                }
            }

            // Bottom Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Gallery Thumbnail
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF111827))
                        .border(2.dp, Color.White, RoundedCornerShape(8.dp))
                ) {
                    if (state.capturedPhotos.isNotEmpty()) {
                        AsyncImage(
                            model = state.capturedPhotos.last(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                // Shutter
                ShutterButton(
                    imageCapture = imageCapture,
                    executor = cameraExecutor,
                    outputDirectory = CameraUtils.getOutputDirectory(context) ?: File(""),
                    onPhotoCaptured = { uri -> onEvent(CaptureUiEvent.OnPhotoCaptured(uri)) }
                )

                // Done
                DoneButton(
                    isEnabled = state.capturedPhotos.isNotEmpty(),
                    onClick = { onEvent(CaptureUiEvent.OnContinueToReview) }
                )
            }
        }

        if (showSettings) {
            CameraSettings(
                state = state,
                onEvent = onEvent,
                onDismiss = { showSettings = false }
            )
        }
    }
}