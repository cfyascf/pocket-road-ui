package com.example.pocket_road_ui.data.interfaces

import com.example.pocket_road_ui.data.remote.dto.ApiResponse
import com.example.pocket_road_ui.data.remote.dto.CarDetailsDto
import com.example.pocket_road_ui.data.remote.dto.RegisterCarRequest
import com.example.pocket_road_ui.data.remote.dto.UserCardexDto

interface ICardexRepository {
    suspend fun getCardex(userId: String): Result<ApiResponse<UserCardexDto?>>
    suspend fun getCarDetails(carId: String): Result<ApiResponse<CarDetailsDto?>>
    suspend fun registerCar(registerCarRequest: RegisterCarRequest): Result<ApiResponse<Unit>>
}