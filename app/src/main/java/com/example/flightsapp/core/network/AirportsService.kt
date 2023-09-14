package com.example.flightsapp.core.network

import com.example.flightsapp.core.data.source.network.airports.models.AirportsNetworkResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AirportsService {
    @GET("/v1/reference-data/locations")
    suspend fun getAirports(
        @Query("subType") subType: String = "CITY",
        @Query("keyword") keyword: String
    ): Response<AirportsNetworkResponse>
}
