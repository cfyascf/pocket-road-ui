package com.example.pocket_road_ui.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class CarDto(
    val id: String,

    @SerializedName("brand")
    val carBrand: String?,

    @SerializedName("model")
    val carModel: String?,

    @SerializedName("rarity")
    val carRarity: String?,

    @SerializedName("year")
    val carYear: String?,

    @SerializedName("photo_path")
    val photoPath: String?
)