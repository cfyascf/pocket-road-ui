package com.example.pocket_road_ui.ui.screens.home.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pocket_road_ui.ui.theme.AppColors

@Composable
fun HomeFooter() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp) // Altura para acomodar o botão flutuante
    ) {
        // Fundo da Barra (Vidro/Blur)
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(64.dp),
            color = AppColors.Gray900.copy(alpha = 0.95f),
            border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.Gray800)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botão Esquerda
                NavIcon(
                    icon = Icons.Default.DirectionsCar,
                    label = "Garagem",
                    isSelected = true
                )

                // Espaço para o botão central
                Spacer(modifier = Modifier.width(48.dp))

                // Botão Direita
                NavIcon(
                    icon = Icons.Default.Person,
                    label = "Perfil",
                    isSelected = false
                )
            }
        }

        // Botão Central Flutuante (Scanner)
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-10).dp) // Sobe um pouco pra fora da barra
        ) {
            FloatingActionButton(
                onClick = { /* Open Scanner */ },
                containerColor = AppColors.Red600,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier
                    .size(64.dp)
                    .border(4.dp, AppColors.Gray950, CircleShape),
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Scan",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun NavIcon(icon: ImageVector, label: String, isSelected: Boolean) {
    val color = if (isSelected) AppColors.Red500 else AppColors.Gray400

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            fontSize = 10.sp,
            color = color,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}