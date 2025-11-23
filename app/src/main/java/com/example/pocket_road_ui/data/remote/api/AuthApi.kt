package com.example.pocket_road_ui.data.remote.api

import com.example.pocket_road_ui.data.remote.dto.ApiResponse
import com.example.pocket_road_ui.data.remote.dto.LoginRequest
import com.example.pocket_road_ui.data.remote.dto.RegisterRequest
import com.example.pocket_road_ui.domain.models.User
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/login")
    suspend fun login(@Body request: LoginRequest): ApiResponse<String?>

    @POST("/api/register")
    suspend fun register(@Body request: RegisterRequest): ApiResponse<User?>
}