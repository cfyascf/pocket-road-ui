package com.example.pocket_road_ui.domain.models

import com.example.pocket_road_ui.domain.enums.CarRarity

data class Car(
    val id: String,
    val model: String,
    val brand: String,
    val year: String,
    val rarity: CarRarity,
    val photoPath: String
)