package com.example.pocket_road_ui.ui.screens.capture.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lightbulb
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
            .background(AppColors.Gray950)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(48.dp))

        // 1. Large Red Error Icon
        Box(
            modifier = Modifier
                .size(96.dp)
                .background(Color(0xFFEF4444).copy(alpha = 0.1f), CircleShape) // Red-500/10
                .border(1.dp, Color(0xFFEF4444).copy(alpha = 0.2f), CircleShape),
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

        // 2. Title & Subtitle
        Text(
            text = "Scan Failed",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "We couldn't identify a car in this image. It might be too dark, blurry, or obstructed.",
            color = AppColors.Gray400,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp),
            lineHeight = 22.sp
        )

        Spacer(Modifier.height(48.dp))

        // 3. Tips Card (Replacing Expert Analysis)
        Card(
            colors = CardDefaults.cardColors(containerColor = AppColors.Gray900),
            border = BorderStroke(1.dp, AppColors.Gray800),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                // Header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color(0xFF3B82F6).copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = Color(0xFF60A5FA), // Blue-400
                            modifier = Modifier.size(16.dp)
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

                // Tips List
                TipItem("The car has been sent to our community experts for manual verification.")
                Spacer(Modifier.height(12.dp))
                TipItem("This process takes ~24h.")
            }
        }

        Spacer(Modifier.weight(1f)) // Push button to bottom

        // 4. Single Navigation Button (Secondary Style)
        Button(
            onClick = { onEvent(CaptureUiEvent.OnGoToGarage) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            // Dark gray background with border (Secondary/Neutral look)
            colors = ButtonDefaults.buttonColors(containerColor = AppColors.Gray800),
            border = BorderStroke(1.dp, AppColors.Gray700),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Text("Return to Garage", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
fun TipItem(text: String) {
    Row(verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier
                .padding(top = 8.dp) // Align dot with text baseline
                .size(6.dp)
                .background(AppColors.Gray700, CircleShape)
        )
        Spacer(Modifier.width(12.dp))
        Text(
            text = text,
            color = AppColors.Gray400,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}

@Preview
@Composable
fun FailureViewPreview() {
    FailureView(onEvent = {})
}

//Column(
//modifier = Modifier.padding(16.dp)
//) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier.padding(bottom = 12.dp)
//    ) {
//        // Blue Shield Icon
//        Box(
//            modifier = Modifier
//                .size(40.dp)
//                .background(Color(0xFF3B82F6).copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
//            contentAlignment = Alignment.Center
//        ) {
//            Icon(
//                imageVector = Icons.Default.Shield,
//                contentDescription = null,
//                tint = Color(0xFF60A5FA), // Blue-400
//                modifier = Modifier.size(18.dp)
//            )
//        }
//        Spacer(Modifier.width(16.dp))
//        Text(
//            text = "Expert Analysis",
//            color = Color.White,
//            fontWeight = FontWeight.Bold,
//            fontSize = 16.sp
//        )
//    }
//    Text(
//        text = "Send this to our community experts for manual verification (takes ~24h).",
//        color = Color(0xFF9CA3AF), // Gray 400
//        fontSize = 14.sp,
//        lineHeight = 20.sp
//    )
//}