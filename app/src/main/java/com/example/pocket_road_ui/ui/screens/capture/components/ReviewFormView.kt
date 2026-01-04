package com.example.pocket_road_ui.ui.screens.capture.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pocket_road_ui.ui.screens.capture.CaptureUiEvent
import com.example.pocket_road_ui.ui.screens.capture.CaptureUiState

@Composable
fun ReviewFormView(
    state: CaptureUiState,
    onEvent: (CaptureUiEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF111827)) // Dark Gray bg
            .padding(16.dp)
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            IconButton(onClick = { onEvent(CaptureUiEvent.OnBackToCamera) }) {
                Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
            }
            Text("Review & Details", style = MaterialTheme.typography.titleLarge, color = Color.White)
        }

        // Photo Grid (Horizontal Scroll)
        Text("Your Photos", color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(120.dp)
        ) {
            items(state.capturedPhotos) { uri ->
                Box(modifier = Modifier.aspectRatio(1f)) {
                    AsyncImage(
                        model = uri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp))
                    )
                    // Delete Button
                    IconButton(
                        onClick = { onEvent(CaptureUiEvent.OnRemovePhoto(uri)) },
                        modifier = Modifier.align(Alignment.TopEnd).size(24.dp).background(Color.Black.copy(0.6f), CircleShape)
                    ) {
                        Icon(Icons.Default.Close, "Remove", tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Hints Form
        Text("Help the AI (Optional)", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Bold)
        Text("Provide hints if the car is modified or rare.", color = Color.Gray, fontSize = 12.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.hints.brand,
            onValueChange = { onEvent(CaptureUiEvent.OnBrandChanged(it)) },
            label = { Text("Brand (e.g. BMW)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color(0xFF10B981),
                unfocusedBorderColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = state.hints.model,
            onValueChange = { onEvent(CaptureUiEvent.OnModelChanged(it)) },
            label = { Text("Model (e.g. M3 GTR)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color(0xFF10B981),
                unfocusedBorderColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = state.hints.year,
            onValueChange = { onEvent(CaptureUiEvent.OnYearChanged(it)) },
            label = { Text("Year") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color(0xFF10B981),
                unfocusedBorderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        // Submit Button
        Button(
            onClick = { onEvent(CaptureUiEvent.OnSubmitAnalysis) },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
        ) {
            Icon(Icons.Default.Bolt, null)
            Spacer(Modifier.width(8.dp))
            Text("Identify Car", fontWeight = FontWeight.Bold)
        }
    }
}