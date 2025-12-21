package com.example.pocket_road_ui.data.interfaces

import com.example.pocket_road_ui.data.remote.dto.ApiResponse
import com.example.pocket_road_ui.domain.models.User

interface IAuthRepository {
    suspend fun login(username: String, password: String): Result<ApiResponse<String?>>
    suspend fun register(username: String, email: String, password: String): Result<ApiResponse<User?>>
}