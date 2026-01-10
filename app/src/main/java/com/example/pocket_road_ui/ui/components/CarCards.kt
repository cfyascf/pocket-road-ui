package com.example.pocket_road_ui.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.pocket_road_ui.domain.enums.CarRarity
import com.example.pocket_road_ui.domain.models.Car
import com.example.pocket_road_ui.ui.extensions.toDisplay
import com.example.pocket_road_ui.ui.theme.AppColors
import com.example.pocket_road_ui.ui.theme.AppTypography

@Composable
fun CarCard(car: Car, onClick: (carId: String) -> Unit) {

    Box(
        modifier = Modifier
            .aspectRatio(0.8f)
            .clip(RoundedCornerShape(16.dp))
            .background(AppColors.Gray900)
            .border(1.dp, AppColors.Gray800, RoundedCornerShape(16.dp))
            .clickable(enabled = true) {
                onClick(car.id)
            }
        ) {

        Image(
            painter = rememberAsyncImagePainter(car.photoPath),
            contentDescription = car.model,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.4f
        )

        // gradient overlay (so you can read the text)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Transparent,
                            AppColors.Gray950.copy(alpha = 0.9f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        ) {

            RarityBadge(car.rarity)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = car.model,
                style = AppTypography.ScreenTitle.copy(fontSize = 14.sp),
                maxLines = 1
            )
            Text(
                text = "${car?.brand?.uppercase().toDisplay()} • ${car?.year.toDisplay()}",
                style = AppTypography.Tagline.copy(fontSize = 10.sp)
            )
        }
    }
}

@Preview
@Composable
fun CarCardPreview() {
    val car = Car(
        "abuble",
        "Ka",
        "Ford",
        "2018",
        CarRarity.COMMON,
        "https://ckecu.com/wp-content/uploads/2022/03/Ford-Ka-800x620.jpg"
    )

    CarCard(car, onClick = {})
}