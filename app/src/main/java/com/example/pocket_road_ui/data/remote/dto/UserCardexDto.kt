package com.example.pocket_road_ui.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserCardexDto (
    @SerializedName("total_price")
    val garageValue: Double,

    @SerializedName("quantity")
    val capturedCount: Int,

    @SerializedName("ranking")
    val ranking: String,

    @SerializedName("cardex")
    val cars: List<CarDto>
)