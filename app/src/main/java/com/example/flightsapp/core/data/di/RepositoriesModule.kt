package com.example.flightsapp.core.data.di

import com.example.flightsapp.core.data.repositories.airports.AirportsRepository
import com.example.flightsapp.core.data.repositories.airports.AirportsRepositoryImpl
import com.example.flightsapp.core.data.repositories.auth.AuthenticationRepository
import com.example.flightsapp.core.data.repositories.auth.AuthenticationRepositoryImpl
import com.example.flightsapp.core.data.repositories.flightOffers.FlightOffersRepository
import com.example.flightsapp.core.data.repositories.flightOffers.FlightOffersRepositoryImpl
import com.example.flightsapp.core.data.repositories.userPreferences.UserPreferencesRepository
import com.example.flightsapp.core.data.repositories.userPreferences.UserPreferencesRepositoryImpl
import com.example.flightsapp.core.data.repositories.userRepository.UserRepository
import com.example.flightsapp.core.data.repositories.userRepository.UserRepositoryImpl
import com.example.flightsapp.core.data.source.remote.SettingsDataSource
import com.example.flightsapp.core.data.source.remote.SettingsRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {
    @Binds
    fun bindFirebaseAuthRepository(
        firebaseRepositoryImpl: AuthenticationRepositoryImpl
    ): AuthenticationRepository

    @Binds
    fun bindFirestoreRepository(
        firestoreRepositoryImpl: SettingsRemoteDataSource
    ): SettingsDataSource

    @Binds
    fun bindUserPreferencesRepository(
        userPreferencesRepositoryImpl: UserPreferencesRepositoryImpl
    ): UserPreferencesRepository

    @Binds
    fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    fun bindFlightOffersRepository(
        flightOffersRepositoryImpl: FlightOffersRepositoryImpl
    ): FlightOffersRepository

    @Binds
    fun bindAirportsRepository(
        airportsRepositoryImpl: AirportsRepositoryImpl
    ): AirportsRepository
}
