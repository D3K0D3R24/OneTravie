package com.example.appturismo.data.PlaceDetailProvider

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceDetailService {
    @GET("place/details/json")
    suspend fun getPlaceDetails(
        @Query("place_id") place_id: String,
        @Query("key") apiKey: String
    ): Response<placeDetailProvider>
}