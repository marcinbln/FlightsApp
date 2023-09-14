package com.example.flightsapp.core.data.repositories.airports

import com.example.flightsapp.core.common.WorkResult
import com.example.flightsapp.core.data.di.AppDispatchers
import com.example.flightsapp.core.data.di.Dispatcher
import com.example.flightsapp.core.data.source.network.airports.AirportsDataSource
import com.example.flightsapp.core.model.Airport
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AirportsRepositoryImpl @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val dataSource: AirportsDataSource
) : AirportsRepository {
    override fun getAirports(keyword: String): Flow<WorkResult<List<Airport>>> =
        flow {
            emit(
                dataSource.getAirports(keyword = keyword)
            )
        }.flowOn(ioDispatcher)
}
