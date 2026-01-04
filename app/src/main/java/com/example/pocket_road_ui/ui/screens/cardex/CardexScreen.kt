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
import com.example.pocket_road_ui.domain.enums.AppTab
import com.example.pocket_road_ui.domain.enums.CarRarity
import com.example.pocket_road_ui.domain.models.Car
import com.example.pocket_road_ui.domain.models.CardexKpis
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
    onNavigateToProfileScreen: () -> Unit,
    onNavigateToLoginScreen: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffects.collect { effect ->
            when (effect) {
                is CardexSideEffect.NavigateToCarDetails -> onNavigateToCarDetail(effect.carId)
                is CardexSideEffect.NavigateToLogin -> onNavigateToLoginScreen()
            }
        }
    }

    CardexScreenContent(
        state = state,
        onCarClicked = viewModel::onCarClicked,
        onSortClicked = { /* TODO: viewModel.onSortClicked() */ },
        onNavigateToCardexScreen = onNavigateToCardexScreen,
        onNavigateToCaptureScreen = onNavigateToCaptureScreen,
        onNavigateToProfileScreen = onNavigateToProfileScreen
    )
}

@Composable
fun CardexScreenContent(
    state: CardexUiState,
    onCarClicked: (String) -> Unit,
    onSortClicked: () -> Unit,
    onNavigateToCardexScreen: () -> Unit,
    onNavigateToCaptureScreen: () -> Unit,
    onNavigateToProfileScreen: () -> Unit,
) {
    Scaffold(
        containerColor = AppColors.Gray950,
        topBar = { CardexHeader() },
        bottomBar = {
            Navbar(
                selectedTab = AppTab.CARDEX,
                onNavigateToCardexScreen = onNavigateToCardexScreen,
                onNavigateToCaptureScreen = onNavigateToCaptureScreen,
                onNavigateToProfileScreen = onNavigateToProfileScreen
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            StatsRow(
                capturedCount = state.kpis.capturedCount,
                garageValue = state.kpis.garageValue,
                ranking = state.kpis.ranking
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
                    modifier = Modifier.clickable(onClick = onSortClicked)
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
                    CarCard(car) { onCarClicked(car.id) }
                }
            }
        }
    }
}

@Preview
@Composable
fun CardexScreenPreview() {
    val fakeKpis = CardexKpis(
        capturedCount = 12,
        garageValue = 1250000.0,
        ranking = "#42"
    )

    val fakeCars = listOf(
        Car("1", "Fusca", "Chevrolet", CarRarity.UNCOMMON, "https://wallup.net/wp-content/uploads/2019/09/245378-2013-volkswagen-kombi-last-edition-bus-van.jpg"),
        Car("2", "Kombi", "Volkswagen", CarRarity.UNCOMMON, "https://wallup.net/wp-content/uploads/2019/09/245378-2013-volkswagen-kombi-last-edition-bus-van.jpg"),
        Car("3", "Brasilia", "Volkswagen", CarRarity.UNCOMMON, "https://wallup.net/wp-content/uploads/2019/09/245378-2013-volkswagen-kombi-last-edition-bus-van.jpg"),
        Car("4", "Opala", "Chevrolet", CarRarity.RARE, "https://wallup.net/wp-content/uploads/2019/09/245378-2013-volkswagen-kombi-last-edition-bus-van.jpg")
    )

    val fakeState = CardexUiState(
        kpis = fakeKpis,
        cars = fakeCars
    )

    CardexScreenContent(
        state = fakeState,
        onCarClicked = {},
        onSortClicked = {},
        onNavigateToCardexScreen = {},
        onNavigateToCaptureScreen = {},
        onNavigateToProfileScreen = {}
    )
}
