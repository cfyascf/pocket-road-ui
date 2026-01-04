package com.example.pocket_road_ui.ui.screens.capture.views

import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
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
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.AsyncImage
import com.example.pocket_road_ui.ui.screens.capture.CaptureUiEvent
import com.example.pocket_road_ui.ui.screens.capture.CaptureUiState
import com.example.pocket_road_ui.ui.theme.AppDimensions
import com.example.pocket_road_ui.utils.CameraUtils
import java.util.concurrent.Executors

@Composable
fun CameraView(
    state: CaptureUiState,
    onEvent: (CaptureUiEvent) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val isPreview = LocalInspectionMode.current // 1. Detect Preview Mode

    // Only initialize executor if NOT in preview
    val cameraExecutor = remember(isPreview) {
        if (!isPreview) Executors.newSingleThreadExecutor() else null
    }

    DisposableEffect(Unit) {
        onDispose {
            cameraExecutor?.shutdown()
        }
    }

    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {

        // 2. Conditional Rendering
        if (isPreview) {
            // Placeholder for Preview Mode (Prevents crash)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Text("Camera Preview Area", color = Color.White)
            }
        } else {
            // Real Camera for Device
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    val previewView = PreviewView(ctx).apply {
                        scaleType = PreviewView.ScaleType.FILL_CENTER
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }

                    // Use applicationContext for API 34+ compatibility
                    val cameraProviderFuture = androidx.camera.lifecycle.ProcessCameraProvider.getInstance(ctx.applicationContext)
                    cameraProviderFuture.addListener({
                        val cameraProvider = cameraProviderFuture.get()
                        val preview = Preview.Builder().build().also {
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }
                        imageCapture = ImageCapture.Builder().build()
                        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                        try {
                            cameraProvider.unbindAll()
                            cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageCapture)
                        } catch (exc: Exception) { /* Handle error */ }
                    }, androidx.core.content.ContextCompat.getMainExecutor(ctx))
                    previewView
                }
            )
        }

        // 3. Grid Overlay (Visible in both)
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val color = Color.White.copy(alpha = 0.2f)
            val strokeWidth = 1.dp.toPx()

            drawLine(color, Offset(width / 3, 0f), Offset(width / 3, height), strokeWidth)
            drawLine(color, Offset(2 * width / 3, 0f), Offset(2 * width / 3, height), strokeWidth)
            drawLine(color, Offset(0f, height / 3), Offset(width, height / 3), strokeWidth)
            drawLine(color, Offset(0f, 2 * height / 3), Offset(width, 2 * height / 3), strokeWidth)
        }

        // 4. UI Overlay (Visible in both)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = AppDimensions.statusBarTopPadding + 48.dp, bottom = AppDimensions.navBarBottomPadding + 32.dp, start = 24.dp, end = 24.dp),
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
                ) {
                    Icon(
                        imageVector = if (state.isFlashOn) Icons.Default.Bolt else Icons.Default.FlashOff,
                        contentDescription = "Flash",
                        tint = if (state.isFlashOn) Color(0xFFFACC15) else Color.White
                    )
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
                    onClick = { /* Settings */ },
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Black.copy(alpha = 0.4f), CircleShape)
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

                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(4.dp, Color.White, CircleShape)
                        .clickable {
                            if (!isPreview) {
                                val outputDir = CameraUtils.getOutputDirectory(context)
                                imageCapture?.let { capture ->
                                    cameraExecutor?.let { executor ->
                                        CameraUtils.takePhoto(capture, outputDir, executor,
                                            onImageCaptured = { uri -> onEvent(CaptureUiEvent.OnPhotoCaptured(uri)) },
                                            onError = { }
                                        )
                                    }
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier.size(64.dp).background(Color.White, CircleShape))
                }

                val isEnabled = state.capturedPhotos.isNotEmpty()
                IconButton(
                    onClick = { onEvent(CaptureUiEvent.OnContinueToReview) },
                    enabled = isEnabled,
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            if (isEnabled) Color(0xFF10B981) else Color.Gray.copy(alpha = 0.5f),
                            CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Done",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
fun CameraViewPreview() {
    CameraView(
        state = CaptureUiState(),
        onEvent = {}
    )
}