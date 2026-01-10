package com.example.pocket_road_ui.ui.screens.capture.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DoneButton(
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isEnabled) Color(0xFF10B981) else Color.Gray.copy(alpha = 0.5f),
        label = "Button Color"
    )

    Box(
        modifier = modifier
            .size(48.dp)
            .shadow(
                elevation = if (isEnabled) 10.dp else 0.dp,
                shape = CircleShape,
                ambientColor = Color(0xFF10B981),
                spotColor = Color(0xFF10B981)
            )
            .background(backgroundColor, CircleShape)
            .clip(CircleShape)
            .clickable(enabled = isEnabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Done",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}