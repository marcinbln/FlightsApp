package com.example.flightsapp.core.data.di

import com.example.flightsapp.core.data.source.network.token.TokenManager
import com.example.flightsapp.core.data.source.network.token.TokenManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface TokenModule {
    @Binds
    @Singleton
    fun bindTokenManager(tokenManagerImpl: TokenManagerImpl): TokenManager
}
