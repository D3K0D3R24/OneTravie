package com.example.appturismo.data

import com.example.appturismo.data.nearbyPlacesProvider.nearbyplacesprovider
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesApiService {
    @GET("place/nearbysearch/json")
    suspend fun getNearbySearch(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String,
        @Query("key") apiKey: String
    ): Response<nearbyplacesprovider>
}
