package com.example.pocket_road_ui.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.pocket_road_ui.ui.theme.AppColors
import com.example.pocket_road_ui.ui.theme.AppTypography

@Composable
fun Title(subtitle: Boolean) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.White)) {
                append("AUTO")
            }
            withStyle(style = SpanStyle(color = AppColors.Red500)) {
                append("DEX")
            }
        },
        style = AppTypography.Logo
    )

    if(subtitle) {
        Text(
            text = "CAPTURE • COLECIONE • COMPITA",
            style = AppTypography.Tagline,
            modifier = Modifier.padding(top = 8.dp, bottom = 48.dp)
        )
    }
}