package com.example.pocket_road_ui.domain.models

import com.example.pocket_road_ui.domain.enums.CarRarity

data class CarDetails(
    val model: String,
    val brand: String,
    val rarity: CarRarity? = null,
    val photoPath: String? = null,
    val aspiration: String? = null,
    val cityFuelEconomy: Double? = null,
    val cylinders: Int? = null,
    val drivetrain: String? = null,
    val engineDisplacement: String? = null,
    val enginePlacement: String? = null,
    val highwayFuelEconomy: Double? = null,
    val horsepower: Int? = null,
    val powertrain: String? = null,
    val price: Double? = null,
    val topSpeed: Double? = null,
    val torque: Double? = null,
    val zeroToHundred: Double? = null,
    val engineConfiguration: String? = null
)