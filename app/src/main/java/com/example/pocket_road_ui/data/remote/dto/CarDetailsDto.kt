package com.example.pocket_road_ui.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CarDetailsDto(
    val model: String,
    val brand: String,
    val rarity: String?,
    val photoPath: String?,
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