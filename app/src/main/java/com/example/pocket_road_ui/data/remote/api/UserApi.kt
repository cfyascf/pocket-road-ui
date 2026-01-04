package com.example.pocket_road_ui.data.remote.api

import com.example.pocket_road_ui.data.remote.dto.ApiResponse
import com.example.pocket_road_ui.data.remote.dto.UserCardexDto
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    @POST("/api/users/me/summary")
    suspend fun getUserProfileSummary(): ApiResponse<UserCardexDto?>
}