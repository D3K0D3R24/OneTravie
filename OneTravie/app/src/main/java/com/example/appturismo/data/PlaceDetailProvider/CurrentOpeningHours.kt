package com.example.appturismo.data.PlaceDetailProvider

data class CurrentOpeningHours(
    val open_now: Boolean,
    val periods: List<Period>,
    val weekday_text: List<String>
)