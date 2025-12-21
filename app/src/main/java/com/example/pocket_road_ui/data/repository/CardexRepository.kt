package com.example.pocket_road_ui.data.repository

import com.example.pocket_road_ui.data.interfaces.ICardexRepository
import com.example.pocket_road_ui.data.remote.api.CardexApi
import com.example.pocket_road_ui.data.remote.dto.ApiResponse
import com.example.pocket_road_ui.domain.models.Car
import jakarta.inject.Inject

open class CardexRepository @Inject constructor(
    private val api: CardexApi
) : ICardexRepository {
    override suspend fun getCardex(userId: String): Result<ApiResponse<List<Car>?>> {
        return try {
            val response = api.getCardex(userId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}