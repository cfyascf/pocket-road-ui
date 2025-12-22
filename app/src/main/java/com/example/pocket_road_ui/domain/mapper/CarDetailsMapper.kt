package com.example.pocket_road_ui.domain.mapper

import com.example.pocket_road_ui.data.remote.dto.CarDetailsDto
import com.example.pocket_road_ui.domain.enums.CarRarity
import com.example.pocket_road_ui.domain.models.CarDetails

fun CarDetailsDto.toEntity(): CarDetails {
    val carRarity = this.rarity

    var formattedRarity = "UNKNOWN"
    if(carRarity != null) {
        formattedRarity = carRarity.uppercase()
    }

    val rarityEnum = try {
        CarRarity.valueOf(formattedRarity)
    } catch (e: IllegalArgumentException) {
        println("Error mapping rarity: ${this.rarity}")
        CarRarity.entries.firstOrNull() ?: throw e
    }

    return CarDetails(
        brand = this.brand,
        model = this.model,
        rarity = rarityEnum,
        photoPath = this.photoPath,
        aspiration = this.aspiration,
        cityFuelEconomy = this.cityFuelEconomy,
        cylinders = this.cylinders,
        drivetrain = this.drivetrain,
        engineDisplacement = this.engineDisplacement,
        enginePlacement = this.enginePlacement,
        highwayFuelEconomy = this.highwayFuelEconomy,
        horsepower = this.horsepower,
        powertrain = this.powertrain,
        price = this.price,
        topSpeed = this.topSpeed,
        torque = this.torque,
        zeroToHundred = this.zeroToHundred,
        engineConfiguration = this.engineConfiguration
    )
}