package com.example.pocket_road_ui.domain.mapper

import com.example.pocket_road_ui.BuildConfig
import com.example.pocket_road_ui.data.remote.dto.CarDto
import com.example.pocket_road_ui.domain.enums.toCarRarityEnum
import com.example.pocket_road_ui.domain.models.Car

fun CarDto.toEntity(): Car {
    val carRarity = this.carRarity.toCarRarityEnum()

    return Car(
        id = this.id,
        brand = this.carBrand?: "Unknown",
        model = this.carModel?: "Unknown",
        rarity = carRarity,
        year = this.carYear?: "Unknown",
        photoPath = this.photoPath?: BuildConfig.DEFAULT_CAR_PHOTO_PATH,
    )
}