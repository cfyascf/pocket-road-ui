package com.example.pocket_road_ui.domain.mapper

import com.example.pocket_road_ui.data.remote.dto.CarDto
import com.example.pocket_road_ui.domain.enums.CarRarity
import com.example.pocket_road_ui.domain.models.Car
import okhttp3.internal.format
import java.util.Locale

fun CarDto.ToEntity(): Car {
    val carRarity = this.carRarity

    var formattedRarity: String
    if(carRarity == null) {
        formattedRarity = "unknown"
    } else {
        formattedRarity = carRarity.uppercase()
    }

    val rarityEnum = try {
        CarRarity.valueOf(formattedRarity)
    } catch (e: IllegalArgumentException) {
        println("Error mapping rarity: ${this.carRarity}")
        CarRarity.values().firstOrNull() ?: throw e
    }

    return Car(
        id = this.id,
        brand = this.carBrand,
        model = this.carModel,
        rarity = rarityEnum,
        photoPath = this.photoPath,
    )
}