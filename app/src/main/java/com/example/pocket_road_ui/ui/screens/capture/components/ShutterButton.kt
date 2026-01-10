package com.example.pocket_road_ui.ui.screens.capture.components

import androidx.camera.core.ImageCapture
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.concurrent.Executor

@Composable
fun ShutterButton(
    imageCapture: ImageCapture?,
    executor: Executor?,
    outputDirectory: java.io.File,
    onPhotoCaptured: (android.net.Uri) -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        label = "Shutter Scale"
    )

    Box(
        modifier = modifier
            .size(80.dp)
            .scale(scale)
            .clip(CircleShape)
            .border(4.dp, Color.White, CircleShape)
            .clickable(interactionSource = interactionSource, indication = null) {
                if (imageCapture != null && executor != null) {
                    com.example.pocket_road_ui.utils.CameraUtils.takePhoto(
                        imageCapture = imageCapture,
                        outputDirectory = outputDirectory,
                        executor = executor,
                        onImageCaptured = onPhotoCaptured,
                        onError = { /* Log error */ }
                    )
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.size(64.dp).background(Color.White, CircleShape))
    }
}