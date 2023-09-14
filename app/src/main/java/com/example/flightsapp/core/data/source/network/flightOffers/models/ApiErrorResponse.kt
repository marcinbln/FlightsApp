package com.example.flightsapp.core.data.source.network.flightOffers.models

data class ApiErrorResponse(val errors: List<ApiError>) {

    data class ApiError(
        val status: Int,
        val code: Int,
        val title: String,
        val detail: String,
        val source: Source
    )

    data class Source(val pointer: String, val example: String)
}
