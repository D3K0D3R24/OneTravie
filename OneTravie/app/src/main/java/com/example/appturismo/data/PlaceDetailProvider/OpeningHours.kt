package com.example.appturismo.data.PlaceDetailProvider

data class OpeningHours(
    val open_now: Boolean,
    val periods: List<PeriodX>,
    val weekday_text: List<String>
)