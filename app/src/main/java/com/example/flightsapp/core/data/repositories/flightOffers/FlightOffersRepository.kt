package com.example.flightsapp.core.data.repositories.flightOffers

import com.example.flightsapp.core.common.WorkResult
import com.example.flightsapp.core.model.SearchQuery
import com.example.flightsapp.core.model.FlightOffer

interface FlightOffersRepository {
    suspend fun getFlightOffers(
        flightQuery: SearchQuery
    ): WorkResult<List<FlightOffer>>
}
