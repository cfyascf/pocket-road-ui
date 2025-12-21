package com.example.pocket_road_ui.ui.screens.cardex.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pocket_road_ui.ui.theme.AppColors
import com.example.pocket_road_ui.ui.theme.AppDimensions
import com.example.pocket_road_ui.ui.theme.AppTypography

@Composable
fun CardexHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(AppDimensions.statusBarTopPadding + 80.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppDimensions.statusBarTopPadding + 92.dp),
            color = AppColors.Gray900.copy(alpha = 0.95f),
            border = BorderStroke(1.dp, AppColors.Gray800)
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        top = AppDimensions.statusBarTopPadding + 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color.White,
                                    fontStyle = FontStyle.Italic
                                )
                            ) {
                                append("AUTO")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = AppColors.Red500,
                                    fontStyle = FontStyle.Italic
                                )
                            ) {
                                append("DEX")
                            }
                        },
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = (-1).sp
                    )
                    Text(
                        text = "LEVEL 12 SPOTTER",
                        style = AppTypography.Tagline
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(
                        onClick = { }
                    ) {
                        Icon(
                            imageVector = Icons.Default.EmojiEvents,
                            contentDescription = "Rank",
                            tint = Color(0xFFEAB308)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CardexHeaderPreview() {
    CardexHeader()
}