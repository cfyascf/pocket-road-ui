package com.example.pocket_road_ui.ui.screens.cardex.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pocket_road_ui.ui.extensions.toAbbreviatedString
import com.example.pocket_road_ui.ui.extensions.toDisplay
import com.example.pocket_road_ui.ui.theme.AppColors
import com.example.pocket_road_ui.ui.theme.AppTypography

@Composable
fun StatsRow(
    capturedCount: Int,
    garageValue: Double,
    ranking: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            label = "Captured",
            value = capturedCount.toDisplay(),
            valueColor = Color.White,
            modifier = Modifier.weight(1f)
        )
        StatCard(
            label = "Garage Value",
            value = garageValue.toAbbreviatedString(),
            valueColor = Color(0xFF10B981),
            modifier = Modifier.weight(1f)
        )
        StatCard(
            label = "Ranking",
            value = ranking.toDisplay(),
            valueColor = AppColors.Red500,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun StatCard(
    label: String,
    value: String,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(AppColors.Gray900)
            .border(1.dp, AppColors.Gray800, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(
            text = label,
            style = AppTypography.Tagline.copy(fontSize = 10.sp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = AppTypography.ScreenTitle.copy(
                fontSize = 18.sp,
                color = valueColor
            )
        )
    }
}