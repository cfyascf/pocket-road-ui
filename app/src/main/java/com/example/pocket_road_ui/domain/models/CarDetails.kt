package com.example.pocket_road_ui.domain.models

import com.example.pocket_road_ui.domain.enums.CarRarity

data class CarDetails(
    val model: String,
    val brand: String,
    val rarity: CarRarity,
    val photoPath: String,
    val aspiration: String?,
    val cityFuelEconomy: Double?,
    val cylinders: Int?,
    val drivetrain: String?,
    val engineDisplacement: String?,
    val enginePlacement: String?,
    val highwayFuelEconomy: Double?,
    val horsepower: Int?,
    val powertrain: String?,
    val price: Double?,
    val topSpeed: Double?,
    val torque: Double?,
    val zeroToHundred: Double?,
    val engineConfiguration: String?
)