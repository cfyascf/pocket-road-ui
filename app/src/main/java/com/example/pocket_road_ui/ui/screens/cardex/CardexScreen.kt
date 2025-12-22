package com.example.pocket_road_ui.ui.screens.cardex

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pocket_road_ui.ui.components.CarCard
import com.example.pocket_road_ui.ui.components.Navbar
import com.example.pocket_road_ui.ui.screens.cardex.components.CardexHeader
import com.example.pocket_road_ui.ui.screens.cardex.components.StatsRow
import com.example.pocket_road_ui.ui.theme.AppColors
import com.example.pocket_road_ui.ui.theme.AppTypography

@Composable
fun CardexScreen(
    viewModel: CardexViewModel = hiltViewModel(),
    onNavigateToCarDetail: (carId: String) -> Unit,
    onNavigateToCardexScreen: () -> Unit,
    onNavigateToCaptureScreen: () -> Unit,
    onNavigateToProfileScreen: () -> Unit)
{
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = AppColors.Gray950,
        topBar = { CardexHeader() },
        bottomBar = { Navbar(
            { onNavigateToCardexScreen() },
            { onNavigateToCaptureScreen() },
            {onNavigateToProfileScreen() }) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            StatsRow(
                state.capturedCount,
                state.garageValue,
                state.ranking
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "Sua garagem",
                    style = AppTypography.ScreenTitle.copy(fontSize = 18.sp)
                )
                Text(
                    text = "Ordenar",
                    style = AppTypography.Tagline.copy(
                        color = AppColors.Red500,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.clickable { }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(state.cars) { car ->
                    CarCard(car) { onNavigateToCarDetail(car.id) }
                }
            }
        }
    }
}

@Preview
@Composable
fun CardexScreenPreview() {
    CardexScreen(
        onNavigateToCarDetail = {},
        onNavigateToCardexScreen = {},
        onNavigateToCaptureScreen = {},
        onNavigateToProfileScreen = {})
}