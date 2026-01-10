package com.example.pocket_road_ui.ui.screens.capture.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pocket_road_ui.ui.screens.capture.CaptureUiEvent
import com.example.pocket_road_ui.ui.screens.capture.CaptureUiState
import com.example.pocket_road_ui.ui.theme.AppDimensions

@Composable
fun ReviewFormView(
    state: CaptureUiState,
    onEvent: (CaptureUiEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF111827)) // Gray 950
            .padding(top = AppDimensions.statusBarTopPadding + 24.dp, bottom = AppDimensions.navBarBottomPadding + 24.dp, start = 24.dp, end = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            IconButton(
                onClick = { onEvent(CaptureUiEvent.OnBackToCamera) },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
            }
            Text(
                text = "Review & Details",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        // Photo Section Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text("Captured Photos", color = Color(0xFF9CA3AF), style = MaterialTheme.typography.bodyMedium)
            Text(
                "${state.capturedPhotos.size} Selected",
                color = Color(0xFF10B981),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
            )
        }

        // Horizontal List with Placeholder
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.height(112.dp).padding(bottom = 32.dp)
        ) {
            // Actual Photos
            items(state.capturedPhotos) { uri ->
                Box(modifier = Modifier.width(112.dp).fillMaxHeight()) {
                    AsyncImage(
                        model = uri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, Color(0xFF374151), RoundedCornerShape(12.dp))
                    )
                    // Remove Button
                    IconButton(
                        onClick = { onEvent(CaptureUiEvent.OnRemovePhoto(uri)) },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                            .size(24.dp)
                            .background(Color.Black.copy(0.6f), CircleShape)
                    ) {
                        Icon(Icons.Default.Close, "Remove", tint = Color.White, modifier = Modifier.size(14.dp))
                    }
                }
            }

            // "Add" Placeholder (UI Only - click goes back to camera)
            item {
                Column(
                    modifier = Modifier
                        .width(112.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp))
                        .border(2.dp, Color(0xFF374151), RoundedCornerShape(12.dp))
                        .clickable { onEvent(CaptureUiEvent.OnBackToCamera) },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.CameraAlt, null, tint = Color(0xFF6B7280))
                    Text("Add", color = Color(0xFF6B7280), fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
                }
            }
        }

        // "Help the AI" Card
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1F2937).copy(alpha = 0.5f)),
            border = BorderStroke(1.dp, Color(0xFF374151)),
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 8.dp)) {
                    Icon(Icons.Default.AutoAwesome, null, tint = Color(0xFFA78BFA), modifier = Modifier.size(20.dp)) // Purple-400
                    Spacer(Modifier.width(8.dp))
                    Text("Help the AI (Optional)", color = Color.White, fontWeight = FontWeight.Bold)
                }
                Text(
                    "Is this a modified car or a rare model? Providing hints increases accuracy.",
                    color = Color(0xFF9CA3AF),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Input Fields
                DarkInputField(
                    value = state.hints.brand,
                    onValueChange = { onEvent(CaptureUiEvent.OnBrandChanged(it)) },
                    label = "Brand (e.g. BMW)"
                )
                Spacer(Modifier.height(12.dp))
                DarkInputField(
                    value = state.hints.model,
                    onValueChange = { onEvent(CaptureUiEvent.OnModelChanged(it)) },
                    label = "Model"
                )
                Spacer(Modifier.height(12.dp))
                DarkInputField(
                    value = state.hints.year,
                    onValueChange = { onEvent(CaptureUiEvent.OnYearChanged(it)) },
                    label = "Year"
                )
                Spacer(Modifier.height(12.dp))
                DarkInputField(
                    value = state.hints.type,
                    onValueChange = { onEvent(CaptureUiEvent.OnYearChanged(it)) },
                    label = "Type"
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Large Submit Button
        Button(
            onClick = { onEvent(CaptureUiEvent.OnSubmitAnalysis) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF10B981) // Emerald 500
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Icon(Icons.Default.Bolt, null)
            Spacer(Modifier.width(8.dp))
            Text("Identify Car", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
fun DarkInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedContainerColor = Color(0xFF111827), // Gray 900
            unfocusedContainerColor = Color(0xFF111827),
            focusedBorderColor = Color(0xFF10B981), // Emerald
            unfocusedBorderColor = Color(0xFF374151), // Gray 700
            focusedLabelColor = Color(0xFF10B981),
            unfocusedLabelColor = Color(0xFF6B7280)
        ),
        singleLine = true
    )
}

@Preview
@Composable
fun ReviewFormPreview() {
    ReviewFormView(
        state = CaptureUiState(),
        onEvent = {}
    )
}