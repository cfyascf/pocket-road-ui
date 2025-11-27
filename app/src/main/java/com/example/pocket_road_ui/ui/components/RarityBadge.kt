package com.example.pocket_road_ui.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pocket_road_ui.ui.theme.AppColors
import com.example.pocket_road_ui.ui.theme.AppTypography

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