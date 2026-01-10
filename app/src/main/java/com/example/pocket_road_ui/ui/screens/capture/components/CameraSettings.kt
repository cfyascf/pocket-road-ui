package com.example.pocket_road_ui.ui.screens.capture.components

import androidx.camera.core.AspectRatio
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Exposure
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.HdrAuto
import androidx.compose.material.icons.filled.PhotoSizeSelectActual
import androidx.compose.material.icons.filled.Tonality
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pocket_road_ui.ui.screens.capture.CaptureUiEvent
import com.example.pocket_road_ui.ui.screens.capture.CaptureUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraSettings(
    state: CaptureUiState,
    onEvent: (CaptureUiEvent) -> Unit,
    onDismiss: () -> Unit
) {

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF1F2937), // Gray 800
        dragHandle = { BottomSheetDefaults.DragHandle(color = Color.Gray) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .padding(bottom = 32.dp) // Extra padding for nav bar
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Camera Settings",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, null, tint = Color.Gray)
                }
            }

            // 1. Grid Lines
            SettingsToggleRow(
                icon = Icons.Default.GridOn,
                label = "Grid Lines",
                isChecked = state.isGridEnabled,
                onToggle = { onEvent(CaptureUiEvent.OnToggleGrid) }
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                thickness = 0.5.dp,
                color = Color(0xFF374151)
            )

            // 2. HDR Mode
            SettingsToggleRow(
                icon = Icons.Default.HdrAuto,
                label = "HDR Mode",
                isChecked = false,
                onToggle = { onEvent(CaptureUiEvent.OnToggleHdr) }
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                thickness = 0.5.dp,
                color = Color(0xFF374151)
            )

            // 3. Smart Contrast
            SettingsToggleRow(
                icon = Icons.Default.Tonality,
                label = "Smart Contrast",
                isChecked = state.isSmartContrastEnabled,
                onToggle = { onEvent(CaptureUiEvent.OnToggleSmartContrast) }
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                thickness = 0.5.dp,
                color = Color(0xFF374151)
            )

            // 4. Exposure
            Text(
                "Exposure",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Exposure, null, tint = Color(0xFF9CA3AF), modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Slider(
                    value = state.exposureValue,
                    onValueChange = { onEvent(CaptureUiEvent.OnExposureChanged(it)) },
                    valueRange = -2f..2f,
                    steps = 9,
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = Color(0xFF10B981),
                        inactiveTrackColor = Color(0xFF111827)
                    ),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = String.format("%.1f", state.exposureValue),
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.width(30.dp)
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                thickness = 0.5.dp,
                color = Color(0xFF374151)
            )

            // 5. Aspect Ratio Selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.PhotoSizeSelectActual, null, tint = Color(0xFF9CA3AF), modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Text("Aspect Ratio", color = Color.White, fontSize = 16.sp, modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF111827))
                        .padding(4.dp)
                ) {
                    // 4:3 Option
                    RatioOption(
                        label = "4:3",
                        isSelected = state.aspectRatio == AspectRatio.RATIO_4_3,
                        onClick = { onEvent(CaptureUiEvent.OnAspectRatioChanged(AspectRatio.RATIO_4_3)) }
                    )

                    // 16:9 Option
                    RatioOption(
                        label = "16:9",
                        isSelected = state.aspectRatio == AspectRatio.RATIO_16_9,
                        onClick = { onEvent(CaptureUiEvent.OnAspectRatioChanged(AspectRatio.RATIO_16_9)) }
                    )
                }
            }
        }
    }
}

@Composable
fun RatioOption(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (isSelected) Color(0xFF374151) else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            color = if (isSelected) Color.White else Color.Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

@Composable
fun SettingsToggleRow(
    icon: ImageVector,
    label: String,
    isChecked: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = Color(0xFF9CA3AF), modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(label, color = Color.White, fontSize = 16.sp, modifier = Modifier.weight(1f))

        Switch(
            checked = isChecked,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF10B981), // Emerald
                uncheckedThumbColor = Color.Gray,
                uncheckedTrackColor = Color(0xFF111827),
                uncheckedBorderColor = Color(0xFF374151)
            )
        )
    }
}

@Preview
@Composable
fun CameraSettingsPreview() {
    // Create a fake state for the preview
    val fakeState = remember {
        mutableStateOf(
            CaptureUiState()
        )
    }

    // Since CameraSettings is designed as a BottomSheet, we wrap it for a better preview
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f)),
        contentAlignment = Alignment.BottomCenter
    ) {
        CameraSettings(
            state = fakeState.value,
            // In a preview, events just modify the local fake state
            onEvent = { event ->
                val currentState = fakeState.value
                fakeState.value = when (event) {
                    is CaptureUiEvent.OnToggleGrid -> currentState.copy(isGridEnabled = !currentState.isGridEnabled)
                    is CaptureUiEvent.OnToggleSmartContrast -> currentState.copy(isSmartContrastEnabled = !currentState.isSmartContrastEnabled)
                    is CaptureUiEvent.OnExposureChanged -> currentState.copy(exposureValue = event.value)
                    is CaptureUiEvent.OnAspectRatioChanged -> currentState.copy(aspectRatio = event.ratio)
                    else -> currentState
                }
            },
            onDismiss = {} // onDismiss does nothing in a preview
        )
    }
}