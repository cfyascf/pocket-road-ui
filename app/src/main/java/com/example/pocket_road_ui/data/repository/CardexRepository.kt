package com.example.pocket_road_ui.data.repository

import com.example.pocket_road_ui.data.interfaces.ICardexRepository
import com.example.pocket_road_ui.data.remote.api.CardexApi
import com.example.pocket_road_ui.data.remote.dto.ApiResponse
import com.example.pocket_road_ui.data.remote.dto.CarDto
import com.example.pocket_road_ui.data.remote.dto.CarDetailsDto
import com.example.pocket_road_ui.data.remote.dto.RegisterCarRequest
import com.example.pocket_road_ui.data.remote.dto.UserCardexDto
import jakarta.inject.Inject

open class CardexRepository @Inject constructor(
    private val api: CardexApi
) : ICardexRepository {
    override suspend fun getCardex(userId: String): Result<ApiResponse<UserCardexDto?>> {
        return try {
            val response = api.getCardex(userId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCarDetails(carId: String): Result<ApiResponse<CarDetailsDto?>> {
        return try {
            val response = api.getCarDetails(carId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun registerCar(registerCarRequest: RegisterCarRequest): Result<ApiResponse<Unit>> {
        return try {
            val response = api.registerCar(registerCarRequest)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

open class CardexRepositoryMock() : ICardexRepository {

    override suspend fun getCardex(userId: String): Result<ApiResponse<UserCardexDto?>> {
        val userCardexDto = UserCardexDto(
            garageValue = 100000.0,
            capturedCount = 5,
            ranking = "1",
            cars = listOf(
                CarDto(
                    id = "uuid1",
                    carModel = "Fusca",
                    carBrand = "Chevrolet",
                    carRarity = "Comum",
                    photoPath = "https://tse2.mm.bing.net/th/id/OIP.y98jbdv3luA_pvE1RBWoQAHaFT?cb=ucfimg2&ucfimg=1&rs=1&pid=ImgDetMain&o=7&rm=3"
                ),
                CarDto(
                    id = "uuid2",
                    carModel = "Ka",
                    carBrand = "Ford",
                    carRarity = "Comum",
                    photoPath = "https://ckecu.com/wp-content/uploads/2022/03/Ford-Ka-800x620.jpg"
                )
            )
        )
        return Result.success(ApiResponse(
            "mock", status = true, data = userCardexDto))
    }

    override suspend fun getCarDetails(carId: String): Result<ApiResponse<CarDetailsDto?>> {
        TODO("Not yet implemented")
    }

    override suspend fun registerCar(registerCarRequest: RegisterCarRequest): Result<ApiResponse<Unit>> {
        TODO("Not yet implemented")
    }
}