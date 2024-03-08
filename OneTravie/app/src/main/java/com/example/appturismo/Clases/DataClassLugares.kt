package com.example.appturismo.Clases

import android.location.Location
import android.os.Parcelable

data class DataClassLugares(
    val texto: String,
    val vecyniti: String,
    val openingHours: String,
    val imgUrl: String,
    val rating: Double,
    val myLocation: Location?,
    val placeLocation: com.example.appturismo.data.nearbyPlacesProvider.Location,
    val placeId: String
)