package com.example.pocket_road_ui.data.interfaces

import com.example.pocket_road_ui.data.remote.dto.ApiResponse
import com.example.pocket_road_ui.data.remote.dto.AuthResponse

interface IAuthRepository {
    suspend fun login(username: String, password: String): Result<ApiResponse<AuthResponse?>>
    suspend fun register(username: String, email: String, password: String): Result<ApiResponse<AuthResponse?>>
}