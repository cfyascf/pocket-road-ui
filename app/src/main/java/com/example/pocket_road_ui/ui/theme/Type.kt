package com.example.pocket_road_ui.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object AppTypography {

    // 1. The Big Logo (AUTO DEX)
    val Logo = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Black,
        fontSize = 36.sp,
        fontStyle = FontStyle.Italic,
        letterSpacing = (-1).sp
    )

    // 2. The Tagline (CAPTURE • COLECIONE...)
    val Tagline = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        color = AppColors.Gray400,
        letterSpacing = 1.sp
    )

    // 3. Page Titles (Bem-vindo, Criar Conta)
    val ScreenTitle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = androidx.compose.ui.graphics.Color.White
    )

    // 4. Input Labels (USERNAME, EMAIL)
    val InputLabel = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        color = AppColors.Gray400
    )

    // 5. Text inside Inputs
    val InputText = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = androidx.compose.ui.graphics.Color.White
    )

    // 6. Button Text
    val ButtonText = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = androidx.compose.ui.graphics.Color.White
    )
}

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)