package com.example.flightsapp.core.model

data class SearchQuery(
    val originLocationCode: String,
    val destinationLocationCode: String,
    val departureDate: String,
    val returnDate: String?,
    val adults: Int,
    val maxResults: Int
)
