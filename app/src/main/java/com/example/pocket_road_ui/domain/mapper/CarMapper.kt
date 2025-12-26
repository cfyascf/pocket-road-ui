package com.example.pocket_road_ui.domain.mapper

import com.example.pocket_road_ui.data.remote.dto.CarDto
import com.example.pocket_road_ui.domain.enums.toCarRarityEnum
import com.example.pocket_road_ui.domain.models.Car

fun CarDto.toEntity(): Car {
    val carRarity = this.carRarity.toCarRarityEnum()

    return Car(
        id = this.id,
        brand = this.carBrand,
        model = this.carModel,
        rarity = carRarity,
        photoPath = this.photoPath,
    )
}