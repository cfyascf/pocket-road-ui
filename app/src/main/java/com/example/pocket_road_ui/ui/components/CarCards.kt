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
import com.example.pocket_road_ui.ui.screens.cardex.CarMock
import com.example.pocket_road_ui.ui.screens.cardex.mockCars
import com.example.pocket_road_ui.ui.theme.AppColors
import com.example.pocket_road_ui.ui.theme.AppTypography

@Composable
fun CarCard(car: CarMock, onClick: (carId: Int) -> Unit) {
    val borderColor = if (car.unlocked) AppColors.Gray700 else AppColors.Gray800

    Box(
        modifier = Modifier
            .aspectRatio(0.8f) // Proporção 4:5
            .clip(RoundedCornerShape(16.dp))
            .background(AppColors.Gray900)
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable(enabled = car.unlocked) {
                onClick(car.id)
            }
        ) {
        // Imagem de Fundo
        Image(
            painter = rememberAsyncImagePainter(car.imageUrl),
            contentDescription = car.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
                .then(
                    if (!car.unlocked) Modifier.blur(4.dp) else Modifier
                )
                .then(
                    if (!car.unlocked) Modifier.run {
                        // Simula Grayscale com ColorFilter se necessário, ou usar alpha
                        // aqui usarei alpha para simplificar
                        this // Compose nao tem grayscale modifier nativo facil sem colorFilter
                    } else Modifier
                ),
            alpha = if (car.unlocked) 1f else 0.4f
        )

        // Gradiente Overlay (para ler o texto)
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

        // Conteúdo
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        ) {
            if (car.unlocked) {
                RarityBadge(car.rarity)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = car.name,
                    style = AppTypography.ScreenTitle.copy(fontSize = 14.sp),
                    maxLines = 1
                )
                Text(
                    text = car.brand,
                    style = AppTypography.Tagline.copy(fontSize = 10.sp)
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked",
                        tint = AppColors.Gray400,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "DESCONHECIDO",
                        style = AppTypography.Tagline.copy(
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    RarityBadge(car.rarity)
                }
            }
        }
    }
}

@Preview
@Composable
fun CarCardPreview() {
    CarCard(mockCars[0], onClick = {})
}