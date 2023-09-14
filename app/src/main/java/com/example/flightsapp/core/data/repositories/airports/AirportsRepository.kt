package com.example.flightsapp.core.data.repositories.airports

import com.example.flightsapp.core.common.WorkResult
import com.example.flightsapp.core.model.Airport
import kotlinx.coroutines.flow.Flow

interface AirportsRepository {
    fun getAirports(keyword: String): Flow<WorkResult<List<Airport>>>
}
