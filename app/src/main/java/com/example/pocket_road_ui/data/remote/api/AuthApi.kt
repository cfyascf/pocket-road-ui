package com.example.pocket_road_ui.data.remote.api

import com.example.pocket_road_ui.data.remote.dto.ApiResponse
import com.example.pocket_road_ui.data.remote.dto.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/login")
    suspend fun login(@Body request: LoginRequest): ApiResponse<String?>
}