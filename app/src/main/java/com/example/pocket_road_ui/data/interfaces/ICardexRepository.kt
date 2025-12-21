package com.example.pocket_road_ui.data.interfaces

import com.example.pocket_road_ui.data.remote.dto.ApiResponse
import com.example.pocket_road_ui.domain.models.Car

interface ICardexRepository {
    suspend fun getCardex(userId: String): Result<ApiResponse<List<Car>?>>
}