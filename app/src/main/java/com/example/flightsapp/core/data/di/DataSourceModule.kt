package com.example.flightsapp.core.data.di

import com.example.flightsapp.core.data.source.network.airports.AirportsDataSource
import com.example.flightsapp.core.data.source.network.airports.AirportsRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {
    @Binds
    fun bindAirportsNetworkDataSource(
        airportsNetworkDataSourceImpl: AirportsRemoteDataSource
    ): AirportsDataSource
}
