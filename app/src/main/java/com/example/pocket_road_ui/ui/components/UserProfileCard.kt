package com.example.pocket_road_ui.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pocket_road_ui.ui.navigation.AppNavigation
import com.example.pocket_road_ui.ui.screens.profile.UserStatsRow
import com.example.pocket_road_ui.ui.theme.AppColors
import com.example.pocket_road_ui.ui.theme.AppTypography

@Composable
fun UserProfileCard(isMainUser: Boolean = true) {
    val backgroundBrush = if (isMainUser) {
        Brush.horizontalGradient(
            colors = listOf(
                AppColors.Red600.copy(alpha = 0.9f),
                AppColors.Gray900.copy(alpha = 0.9f)
            )
        )
    } else {
        Brush.horizontalGradient(
            colors = listOf(
                AppColors.Gray900,
                AppColors.Gray900.copy(alpha = 0.8f)
            )
        )
    }

    val borderColor = if (isMainUser) {
        AppColors.Red500.copy(alpha = 0.3f)
    } else {
        AppColors.Gray900
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(backgroundBrush)
            .border(1.dp, borderColor, RoundedCornerShape(24.dp))
            .padding(24.dp)
    ) {
        Column() {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(AppColors.Gray800)
                            .border(2.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = AppColors.Gray400,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Textos
                    Column {
                        Text(
                            text = "Você",
                            style = AppTypography.ScreenTitle.copy(fontSize = 20.sp)
                        )
                        Text(
                            text = "Spotter Level 12",
                            style = AppTypography.Tagline.copy(
                                color = AppColors.Red500, // Usando uma cor mais clara se Red500 for muito escuro, ou white
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                // Ranking
                Column(horizontalAlignment = Alignment.End) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = Color(0xFFEAB308), // Yellow
                        modifier = Modifier.size(28.dp)
                    )
                    Text(
                        text = "Rank #840",
                        style = AppTypography.Tagline.copy(fontSize = 10.sp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            UserStatsRow()
        }
    }
}

@Preview
@Composable
fun UserProfileCardPreview() {
    UserProfileCard(false)
}