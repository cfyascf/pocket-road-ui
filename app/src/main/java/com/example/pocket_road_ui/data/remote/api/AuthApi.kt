package com.example.pocket_road_ui.data.remote.api

import com.example.pocket_road_ui.data.remote.dto.ApiResponse
import com.example.pocket_road_ui.data.remote.dto.AuthResponse
import com.example.pocket_road_ui.data.remote.dto.LoginRequest
import com.example.pocket_road_ui.data.remote.dto.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/login")
    suspend fun login(@Body request: LoginRequest): ApiResponse<AuthResponse?>

    @POST("/api/register")
    suspend fun register(@Body request: RegisterRequest): ApiResponse<AuthResponse?>
}