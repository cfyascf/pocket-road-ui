package com.example.pocket_road_ui.ui.screens.cardetails

import com.example.pocket_road_ui.domain.models.CarDetails

data class CarDetailsUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val carDetails: CarDetails? = null
)