package com.example.flightsapp.core.data.source.network.flightOffers

import com.example.flightsapp.core.data.source.network.flightOffers.models.FlightOffersNetworkResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlightOffersService {
    @GET("/v2/shopping/flight-offers")
    suspend fun getFlightOffers(
        @Query("originLocationCode") originLocationCode: String,
        @Query("destinationLocationCode") destinationLocationCode: String,
        @Query("departureDate") departureDate: String,
        @Query("returnDate") returnDate: String? = null,
        @Query("adults") adults: Int,
        @Query("max") max: Int = 20
    ): Response<FlightOffersNetworkResponse>
}
