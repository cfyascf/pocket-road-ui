package com.example.pocket_road_ui.ui.screens.capture.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pocket_road_ui.ui.screens.capture.CaptureUiState

@Composable
fun ProcessingView(state: CaptureUiState) {
    val infiniteTransition = rememberInfiniteTransition(label = "Radar")
    val radarScale by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "RadarScale"
    )
    val radarAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "RadarAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        // Radar Animation
        Box(modifier = Modifier
            .size(200.dp)
            .drawBehind {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFF10B981), Color.Transparent)
                    ),
                    radius = size.width / 2 * radarScale,
                    alpha = radarAlpha
                )
            }
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Central Icon
            Icon(
                imageVector = Icons.Default.Bolt,
                contentDescription = null,
                tint = Color(0xFF10B981),
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Dynamic Text
            Text(
                text = state.processingMessage,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}