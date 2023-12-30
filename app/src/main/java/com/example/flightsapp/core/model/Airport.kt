package com.example.flightsapp.core.model

@Suppress("SpellCheckingInspection")
data class Airport(
    val iata: String = "",
    val cityName: String = "",
    val countryName: String = "",
    val countryCode: String = ""
)
