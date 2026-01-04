package com.example.pocket_road_ui.ui.screens.capture.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pocket_road_ui.ui.screens.capture.CaptureUiEvent
import com.example.pocket_road_ui.ui.theme.AppColors

@Composable
fun FailureView(
    onEvent: (CaptureUiEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF111827)) // Gray 950
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Red Error Icon Container
        Box(
            modifier = Modifier
                .size(96.dp)
                .background(Color(0xFFEF4444).copy(alpha = 0.3f), CircleShape), // Red-500/10
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = Color(0xFFEF4444), // Red-500
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(Modifier.height(24.dp))

        // Title
        Text(
            text = "Not 100% Sure",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(12.dp))

        // Subtitle
        Text(
            text = "The AI couldn't confidently identify this car. It might be too dark, blurry, or heavily modified.",
            color = Color(0xFF9CA3AF), // Gray 400
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(Modifier.height(32.dp))

        // Expert Analysis Card
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1F2937)), // Gray 800
            border = BorderStroke(1.dp, Color(0xFF374151)), // Gray 700
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color(0xFF3B82F6).copy(alpha = 0.2f), CircleShape), // Blue-500/20
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = Color(0xFF60A5FA), // Blue-400
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = "Expert Analysis",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
                Text(
                    text = "Send this to our community experts for manual verification (takes ~24h).",
                    color = Color(0xFF6B7280), // Gray 500
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }
        }

        // Main Button (Blue 600)
        Button(
            onClick = { onEvent(CaptureUiEvent.OnSendForManualReview) },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AppColors.Red600 )
        ) {
            Text("Send for Manual Review", fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(16.dp))

        // Secondary Button (Outlined)
        OutlinedButton(
            onClick = { onEvent(CaptureUiEvent.OnBackToCamera) },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color(0xFF4B5563)), // Gray 600
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
        ) {
            Text("Retake Photos", fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview
@Composable
fun FailureViewPreview() {
    FailureView(onEvent = {})
}