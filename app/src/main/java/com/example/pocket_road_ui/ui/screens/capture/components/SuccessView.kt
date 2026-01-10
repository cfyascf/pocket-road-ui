package com.example.pocket_road_ui.ui.screens.capture.components

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pocket_road_ui.domain.enums.CarRarity
import com.example.pocket_road_ui.ui.components.RarityBadge
import com.example.pocket_road_ui.ui.extensions.toDisplay
import com.example.pocket_road_ui.ui.screens.capture.CaptureUiEvent
import com.example.pocket_road_ui.ui.screens.capture.CaptureUiState
import com.example.pocket_road_ui.ui.theme.AppColors
import com.example.pocket_road_ui.ui.theme.AppDimensions

// Updated to include stats for the "Super Trunfo" look
data class RecognizedCar(
    val brand: String,
    val model: String,
    val year: String,
    val type: String,
    val rarity: CarRarity = CarRarity.COMMON,
    val photoUri: String? = null,
    // Added these to match the design (can be empty/null if API doesn't send them yet)
    val power: String = "--- cv",
    val acceleration: String = "-.- s"
)

@Composable
fun SuccessView(
    state: CaptureUiState,
    onEvent: (CaptureUiEvent) -> Unit
) {
    val car = state.recognizedCar

    Box(
        modifier = Modifier.fillMaxSize().background(AppColors.Gray950)
    ) {
        // 1. IMMERSIVE BACKGROUND (Blurred Image)
        if (car?.photoUri != null) {
            AsyncImage(
                model = car.photoUri,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .then(
                        // Blur only works on Android 12+ (S)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            Modifier.blur(radius = 50.dp)
                        } else {
                            Modifier.background(Color.Black.copy(alpha = 0.5f)) // Fallback
                        }
                    )
                    .padding(bottom = 100.dp) // Offset slightly up
            )
            // Dark overlay to ensure text readability
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
            )
        }

        // 2. MAIN CONTENT
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = AppDimensions.statusBarTopPadding + 16.dp,
                    bottom = AppDimensions.navBarBottomPadding + 16.dp,
                    start = 24.dp,
                    end = 24.dp
                )
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // --- Header ---
            Column(
                modifier = Modifier.padding(bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(82.dp)
                        .background(Color(0xFF22C55E).copy(alpha = 0.2f), CircleShape)
                        .border(1.dp, Color(0xFF22C55E).copy(alpha = 0.3f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color(0xFF4ADE80),
                        modifier = Modifier.size(40.dp)
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Car Identified!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(26.dp))

            // --- THE CARD REVEAL (Hero Image) ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 3f)
                    .clip(RoundedCornerShape(24.dp))
                    .border(1.dp, AppColors.Gray800, RoundedCornerShape(24.dp))
                    .shadow(elevation = 24.dp, spotColor = Color(car?.rarity?.colorHex?.toInt() ?: 0xFFFFFFFF.toInt()).copy(alpha = 0.3f))
            ) {
                // Image
                if (car?.photoUri != null) {
                    AsyncImage(
                        model = car.photoUri,
                        contentDescription = "Captured Car",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(Modifier.fillMaxSize().background(Color.DarkGray))
                }

                // Gradient Overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Transparent,
                                    AppColors.Gray950.copy(alpha = 0.6f),
                                    AppColors.Gray950.copy(alpha = 0.95f)
                                ),
                                startY = 0.3f
                            )
                        )
                )

                // Content Inside Card
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(24.dp)
                ) {
                    if (car != null) {
                        RarityBadge(rarity = car.rarity)
                    }

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = car?.model.toDisplay(),
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = (-1).sp,
                            fontSize = 32.sp
                        ),
                        color = Color.White,
                        lineHeight = 36.sp
                    )

                    Text(
                        text = "${car?.brand?.uppercase().toDisplay()} • ${car?.year.toDisplay()}",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        color = AppColors.Gray400
                    )
                }
            }

            Spacer(Modifier.height(26.dp))

            // --- ACTION BUTTONS ---

            // Primary: Add to Garage
            Button(
                onClick = { onEvent(CaptureUiEvent.OnGoToGarage) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.Red600),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Add to Garage",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(Modifier.width(8.dp))
                    Icon(Icons.Default.ChevronRight, contentDescription = null)
                }
            }

            Spacer(Modifier.height(12.dp))

            // Secondary: Report
            TextButton(
                onClick = { /* onEvent(CaptureUiEvent.OnReport) */ },
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text(
                    text = "Incorrect Match? Report",
                    color = AppColors.Gray400,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview
@Composable
fun SuccessViewPreview() {
    val fakeState = CaptureUiState(
        recognizedCar = RecognizedCar(
            brand = "Ferrari",
            model = "488 Pista",
            year = "2020",
            type = "Supercar",
            rarity = CarRarity.LEGENDARY,
            power = "720 cv",
            acceleration = "2.85 s",
            photoUri = "https://pictures.topspeed.com/IMG/crop/201101/2012-chevrolet-camaro-lfx_1600x0w.jpg"
        )
    )

    SuccessView(state = fakeState, onEvent = {})
}