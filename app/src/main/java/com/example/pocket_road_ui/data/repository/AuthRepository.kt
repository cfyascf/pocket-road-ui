package com.example.pocket_road_ui.data.repository

import com.example.pocket_road_ui.data.interfaces.IAuthRepository
import com.example.pocket_road_ui.data.remote.api.AuthApi
import com.example.pocket_road_ui.data.remote.dto.ApiResponse
import com.example.pocket_road_ui.data.remote.dto.AuthResponse
import com.example.pocket_road_ui.data.remote.dto.LoginRequest
import com.example.pocket_road_ui.data.remote.dto.RegisterRequest
import com.example.pocket_road_ui.domain.models.User
import jakarta.inject.Inject

open class AuthRepository @Inject constructor(
    private val api: AuthApi
) : IAuthRepository {

    override suspend fun login(username: String, password: String): Result<ApiResponse<AuthResponse?>> {
        return try {
            val response = api.login(LoginRequest(username, password))
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): Result<ApiResponse<AuthResponse?>> {
        return try {
            val response = api.register(RegisterRequest(username, email, password))
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
open class AuthRepositoryMock() : IAuthRepository {

    override suspend fun login(
        username: String,
        password: String
    ): Result<ApiResponse<AuthResponse?>> {
        return Result.success(ApiResponse(status = true, message = "mock", data = AuthResponse("1", "token")))
    }

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): Result<ApiResponse<AuthResponse?>> {
        return Result.success(ApiResponse(status = true, message = "mock", data = AuthResponse("1", "token")))
    }
}