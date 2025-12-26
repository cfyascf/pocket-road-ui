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
import androidx.compose.runtime.getValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.pocket_road_ui.domain.enums.CarRarity
import com.example.pocket_road_ui.domain.models.Car
import com.example.pocket_road_ui.domain.models.CarDetails
import com.example.pocket_road_ui.ui.screens.cardetails.components.PaddingBox
import com.example.pocket_road_ui.ui.screens.cardetails.components.SpecRow
import com.example.pocket_road_ui.ui.screens.cardetails.components.StatGridItem
import com.example.pocket_road_ui.ui.theme.AppColors
import com.example.pocket_road_ui.ui.theme.AppDimensions
import com.example.pocket_road_ui.ui.theme.AppTypography
import com.example.pocket_road_ui.util.toDisplay

@Composable
fun CarDetailScreen(
    viewModel: CarDetailsViewModel = hiltViewModel(),
    onClose: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CarDetailContent(
        state = uiState,
        onClose = onClose
    )
}

@Composable
fun CarDetailContent(
    state: CarDetailsUiState,
    onClose: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Gray950)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(state.carDetails?.photoPath.toDisplay()),
                contentDescription = state.carDetails?.model.toDisplay(),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // gradient overlay
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

        // action buttons
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

        // scrolling content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 280.dp)
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
                            text = state.carDetails?.brand?.uppercase().toDisplay(),
                            style = AppTypography.Tagline.copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 2.sp
                            )
                        )
                        Text(
                            text = state.carDetails?.model.toDisplay(),
                            style = AppTypography.ScreenTitle.copy(
                                fontSize = 32.sp,
                                lineHeight = 36.sp
                            )
                        )
                    }
                    RarityBadge(rarity = state.carDetails?.rarity ?: CarRarity.UNKNOWN)
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
                            value = state.carDetails?.horsepower.toDisplay("cv"),
                            color = Color(0xFFEAB308), // Amarelo
                            modifier = Modifier.weight(1f)
                        )
                        StatGridItem(
                            icon = Icons.Default.Speed,
                            label = "0-100 KM/H",
                            value = state.carDetails?.zeroToHundred.toDisplay("s"),
                            color = Color(0xFF3B82F6), // Azul
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        StatGridItem(
                            icon = Icons.Default.BarChart,
                            label = "TORQUE",
                            value = state.carDetails?.torque.toDisplay("kgfm"),
                            color = Color(0xFFEF4444), // Vermelho
                            modifier = Modifier.weight(1f)
                        )
                        StatGridItem(
                            icon = Icons.Default.LocalGasStation,
                            label = "MOTOR",
                            value = state.carDetails?.engineConfiguration.toDisplay(),
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
                        SpecRow("Aspiração", state.carDetails?.aspiration.toDisplay())
                        SpecRow("Motorização", state.carDetails?.powertrain.toDisplay())
                        SpecRow("Cilindrada", state.carDetails?.engineDisplacement.toDisplay())
                        SpecRow("Cilindros", state.carDetails?.cylinders.toDisplay())
                        SpecRow("Tração", state.carDetails?.drivetrain.toDisplay())
                        SpecRow("Posição do Motor", state.carDetails?.enginePlacement.toDisplay())
                        SpecRow("Consumo Urbano", state.carDetails?.cityFuelEconomy.toDisplay())
                        SpecRow("Consumo Rodoviário", state.carDetails?.highwayFuelEconomy.toDisplay())
                        SpecRow("Preço", state.carDetails?.price.toDisplay())
                    }
                }
            }

            Spacer(modifier = Modifier.height(AppDimensions.statusBarTopPadding + 32.dp))
        }
    }
}

@Preview
@Composable
fun CarDetailPreview() {
    val mockState = CarDetailsUiState(
        false, null, CarDetails(
            "DS3",
            "Citroen",
            CarRarity.UNCOMMON,
            "https://tse4.mm.bing.net/th/id/OIP.kIUXDA2p-jigxjO98JypcAHaE7?rs=1&pid=ImgDetMain&o=7&rm=3",
            "Turbo",
            10.0,
            4,
            "SWD",
            "1600",
            "Front",
            14.0,
            165,
            "Combustion",
            65000.0,
            213.0,
            24.0,
            6.8,
            "1.6 L4"
        )
    )

    CarDetailContent(state = mockState, onClose = {})
}