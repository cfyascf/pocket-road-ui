package com.example.pocket_road_ui.ui.screens.cardex

import com.example.pocket_road_ui.domain.models.Car
import com.example.pocket_road_ui.domain.models.CardexKpis

data class CardexUiState(
    val isLoading: Boolean = false,
    val cars: List<Car> = emptyList(),
    val errorMessage: String? = null,

    val kpis: CardexKpis = CardexKpis()
)