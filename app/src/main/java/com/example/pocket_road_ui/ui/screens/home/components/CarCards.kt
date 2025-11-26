package com.example.pocket_road_ui.ui.screens.home.components

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.pocket_road_ui.ui.screens.home.CarMock
import com.example.pocket_road_ui.ui.theme.AppColors
import com.example.pocket_road_ui.ui.theme.AppTypography

@Composable
fun CarCard(car: CarMock) {
    val borderColor = if (car.unlocked) AppColors.Gray700 else AppColors.Gray800

    Box(
        modifier = Modifier
            .aspectRatio(0.8f) // Proporção 4:5
            .clip(RoundedCornerShape(16.dp))
            .background(AppColors.Gray900)
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable(enabled = car.unlocked) { /* Open Details */ }
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
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    RarityBadge(car.rarity)
                }
            }
        }
    }
}

@Composable
fun RarityBadge(rarity: String) {
    val (bgColor, textColor) = when (rarity.lowercase()) {
        "comum" -> Pair(AppColors.Gray700, Color.White)
        "incomum" -> Pair(Color(0xFF22C55E), Color.White) // Green-500
        "raro" -> Pair(Color(0xFF3B82F6), Color.White) // Blue-500
        "exotico" -> Pair(Color(0xFFA855F7), Color.White) // Purple-500
        "lendario" -> Pair(Color(0xFFEAB308), Color.Black) // Yellow-500
        else -> Pair(AppColors.Gray700, Color.White)
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.height(16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 6.dp)
        ) {
            Text(
                text = rarity.uppercase(),
                style = AppTypography.Tagline.copy(
                    fontSize = 8.sp,
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            )
        }
    }
}