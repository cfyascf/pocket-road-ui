package com.example.pocket_road_ui.ui.screens.cardetails

import com.example.pocket_road_ui.ui.components.RarityBadge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.pocket_road_ui.domain.enums.CarRarity
import com.example.pocket_road_ui.domain.models.Car
import com.example.pocket_road_ui.ui.screens.cardetails.components.PaddingBox
import com.example.pocket_road_ui.ui.screens.cardetails.components.SpecRow
import com.example.pocket_road_ui.ui.screens.cardetails.components.StatGridItem
import com.example.pocket_road_ui.ui.theme.AppColors
import com.example.pocket_road_ui.ui.theme.AppTypography

@Composable
fun CarDetailScreen(
    car: Car,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Gray950)
    ) {
        // 1. Imagem de Fundo (Header)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(car.photoPath),
                contentDescription = car.model,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Overlay Gradiente (para transição suave para o preto)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                AppColors.Gray950.copy(alpha = 0.4f),
                                AppColors.Gray950
                            )
                        )
                    )
            )
        }

        // 2. Botões de Ação do Topo
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, start = 24.dp, end = 24.dp), // Padding para StatusBar
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .border(1.dp, Color.White.copy(alpha = 0.1f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White
                )
            }

            IconButton(
                onClick = { /* Share Action */ },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .border(1.dp, Color.White.copy(alpha = 0.1f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    tint = Color.White
                )
            }
        }

        // 3. Conteúdo Rolável
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 280.dp) // Começa um pouco antes da imagem acabar
                .verticalScroll(rememberScrollState())
        ) {
            // Título e Raridade
            PaddingBox {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = car.brand.uppercase(),
                            style = AppTypography.Tagline.copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 2.sp
                            )
                        )
                        Text(
                            text = car.model,
                            style = AppTypography.ScreenTitle.copy(
                                fontSize = 32.sp,
                                lineHeight = 36.sp
                            )
                        )
                    }
                    RarityBadge(rarity = car.rarity.label)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Grid de Status (O "Super Trunfo")
            PaddingBox {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        StatGridItem(
                            icon = Icons.Default.ElectricBolt,
                            label = "POTÊNCIA",
                            value = "525 cv", // Dados mockados, viriam do objeto 'car'
                            color = Color(0xFFEAB308), // Amarelo
                            modifier = Modifier.weight(1f)
                        )
                        StatGridItem(
                            icon = Icons.Default.Speed,
                            label = "0-100 KM/H",
                            value = "3.2s",
                            color = Color(0xFF3B82F6), // Azul
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        StatGridItem(
                            icon = Icons.Default.BarChart,
                            label = "TORQUE",
                            value = "47.4 kgfm",
                            color = Color(0xFFEF4444), // Vermelho
                            modifier = Modifier.weight(1f)
                        )
                        StatGridItem(
                            icon = Icons.Default.LocalGasStation,
                            label = "MOTOR",
                            value = "4.0 Boxer",
                            color = Color(0xFF22C55E), // Verde
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Ficha Técnica Detalhada
            PaddingBox {
                Column {
                    Text(
                        text = "FICHA TÉCNICA",
                        style = AppTypography.Tagline.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(AppColors.Gray900)
                            .border(1.dp, AppColors.Gray800, RoundedCornerShape(16.dp))
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        SpecRow("Preço Estimado", "R$ 1.900.000")
                        SpecRow("Tração", "Traseira (RWD)")
                        SpecRow("Velocidade Máx.", "296 km/h")
                        SpecRow("Câmbio", "PDK 7 Marchas")
                        SpecRow("Peso", "1.450 kg")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botão de Ação
            PaddingBox {
                Button(
                    onClick = { /* View 3D Model */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.Red600),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(8.dp)
                ) {
                    Text(
                        text = "VISUALIZAR EM 3D",
                        style = AppTypography.ButtonText.copy(letterSpacing = 1.sp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Preview
@Composable
fun CarDetailPreview() {
    val mockCar = Car(
        id = "2",
        model = "911 GT3 RS",
        brand = "Porsche",
        rarity = CarRarity.LEGENDARY,
        photoPath = "https://images.unsplash.com/photo-1614162692292-7ac56d7f7f1e"
    )

    CarDetailScreen(car = mockCar, onClose = {})
}