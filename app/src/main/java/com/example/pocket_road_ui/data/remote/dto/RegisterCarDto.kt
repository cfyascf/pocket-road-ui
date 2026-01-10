package com.example.pocket_road_ui.data.remote.dto

import android.graphics.Picture
import android.net.Uri
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.Part
import java.io.File

data class RegisterCarRequest(
    val photos: List<Uri>,
    val modelHint: String,
    val brandHint: String,
    val yearHint: String,
    val typeHint: String,
)