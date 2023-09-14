package com.example.flightsapp.core.data.source.network.airports

import com.example.flightsapp.core.common.WorkResult
import com.example.flightsapp.core.model.Airport

interface AirportsDataSource {
    suspend fun getAirports(keyword: String): WorkResult<List<Airport>>
}
