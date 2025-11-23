package com.example.pocket_road_ui.domain.models

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val password: String,

    @SerializedName("photo_path")
    val photoPath: String,

    @SerializedName("google_id")
    val googleId: String
)