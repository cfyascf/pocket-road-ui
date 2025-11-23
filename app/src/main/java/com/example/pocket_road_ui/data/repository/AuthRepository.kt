package com.example.pocket_road_ui.data.repository

import com.example.pocket_road_ui.data.remote.api.AuthApi
import com.example.pocket_road_ui.data.remote.dto.ApiResponse
import com.example.pocket_road_ui.data.remote.dto.LoginRequest
import jakarta.inject.Inject

interface IAuthRepository {
    suspend fun login(username: String, pass: String): Result<ApiResponse<String?>>
}

open class AuthRepository @Inject constructor(
    private val api: AuthApi
) : IAuthRepository {

    override suspend fun login(username: String, pass: String): Result<ApiResponse<String?>> {
        return try {
            val response = api.login(LoginRequest(username, pass))
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}