package com.example.pocket_road_ui.ui.screens.cardex

import com.example.pocket_road_ui.domain.models.Car

data class CardexUiState(
    val isLoading: Boolean = false,
    val cars: List<Car> = emptyList(),
    val errorMessage: String? = null,

    val capturedCount: String = "0",
    val garageValue: String = "0",
    val ranking: String = "#---"
)