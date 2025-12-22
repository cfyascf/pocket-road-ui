package com.example.pocket_road_ui.data.remote.api

import com.example.pocket_road_ui.data.remote.dto.ApiResponse
import com.example.pocket_road_ui.data.remote.dto.UserCardexDto
import com.example.pocket_road_ui.domain.models.Car
import retrofit2.http.POST
import retrofit2.http.Path

interface CardexApi {
    @POST("/api/cardex/{userId}")
    suspend fun getCardex(@Path("userId") userId: String): ApiResponse<UserCardexDto?>
}