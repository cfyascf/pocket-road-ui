package com.example.pocket_road_ui.data.remote.dto

data class ApiResponse<T> (
    val message: String,
    val status: Boolean,
    val data: T
)