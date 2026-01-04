package com.example.pocket_road_ui.data.remote.dto

data class UserProfileSummaryDto(
    val capturedCars: Int?,
    val following: Int?,
    val followers: Int?,
    val garageValue: Double,
    val level: Int,
    val ranking: Int,
)