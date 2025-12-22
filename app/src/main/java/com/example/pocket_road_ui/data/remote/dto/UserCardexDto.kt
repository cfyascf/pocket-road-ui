package com.example.pocket_road_ui.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserCardexDto (
    @SerializedName("total_price")
    val garageValue: String,

    @SerializedName("quantity")
    val capturedCount: String,

    @SerializedName("ranking")
    val ranking: String,

    @SerializedName("cardex")
    val cars: List<CarDto>
)