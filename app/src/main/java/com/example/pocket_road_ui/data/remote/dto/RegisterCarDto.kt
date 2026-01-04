package com.example.pocket_road_ui.data.remote.dto

import android.graphics.Picture
import com.google.gson.annotations.SerializedName

data class RegisterCarRequest(
    val photos: List<Picture>,

    @SerializedName("model_hint")
    val modelHint: String,

    @SerializedName("brand_hint")
    val brandHint: String,

    @SerializedName("year_hint")
    val yearHint: String,

    @SerializedName("type_hint")
    val typeHint: String,
)