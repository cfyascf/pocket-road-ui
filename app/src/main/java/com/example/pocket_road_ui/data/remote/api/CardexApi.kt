package com.example.pocket_road_ui.data.remote.api

import com.example.pocket_road_ui.data.remote.dto.ApiResponse
import com.example.pocket_road_ui.data.remote.dto.CarDetailsDto
import com.example.pocket_road_ui.data.remote.dto.RegisterCarRequest
import com.example.pocket_road_ui.data.remote.dto.UserCardexDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface CardexApi {
    @POST("/api/cardex/{userId}")
    suspend fun getCardex(@Path("userId") userId: String): ApiResponse<UserCardexDto?>

    @GET("/api/cardex/description/{carId}")
    suspend fun getCarDetails(@Path("carId") carId: String): ApiResponse<CarDetailsDto?>

    @Multipart
    @POST("/api/cardex/add")
    suspend fun registerCar(
        @Part photos: List<MultipartBody.Part>,
        @Part("model_hint") modelHint: RequestBody,
        @Part("brand_hint") brandHint: RequestBody,
        @Part("year_hint") yearHint: RequestBody,
        @Part("type_hint") typeHint: RequestBody,
    ): ApiResponse<Unit>
}