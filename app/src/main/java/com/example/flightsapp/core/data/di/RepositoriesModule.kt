package com.example.flightsapp.core.data.di

import com.example.flightsapp.core.data.repositories.auth.AuthenticationRepository
import com.example.flightsapp.core.data.repositories.auth.AuthenticationRepositoryImpl
import com.example.flightsapp.core.data.repositories.userPreferences.local.UserPreferencesRepository
import com.example.flightsapp.core.data.repositories.userPreferences.local.UserPreferencesRepositoryImpl
import com.example.flightsapp.core.data.repositories.userPreferences.remote.RemoteDataSource
import com.example.flightsapp.core.data.repositories.userPreferences.remote.RemoteDataSourceImpl
import com.example.flightsapp.core.data.repositories.userRepository.UserRepository
import com.example.flightsapp.core.data.repositories.userRepository.UserRepositoryImpl
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
        firestoreRepositoryImpl: RemoteDataSourceImpl
    ): RemoteDataSource

    @Binds
    fun bindUserPreferencesRepository(
        userPreferencesRepositoryImpl: UserPreferencesRepositoryImpl
    ): UserPreferencesRepository

    @Binds
    fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}
