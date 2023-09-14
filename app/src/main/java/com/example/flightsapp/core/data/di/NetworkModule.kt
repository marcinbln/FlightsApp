package com.example.flightsapp.core.data.di

import com.example.flightsapp.core.data.repositories.flightOffers.BASE_URL
import com.example.flightsapp.core.network.AirportsService
import com.example.flightsapp.core.data.source.network.flightOffers.FlightOffersService
import com.example.flightsapp.core.data.source.network.token.TokenManager
import com.example.flightsapp.core.network.TokenAuthenticator
import com.example.flightsapp.core.network.TokenInterceptor
import com.example.flightsapp.core.network.TokenService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient
        .Builder()
        .build()

    @Provides
    @Singleton
    fun provideAuthenticator(
        @Dispatcher(AppDispatchers.IO) ioDispatcher: CoroutineDispatcher,
        tokenManager: TokenManager
    ): TokenAuthenticator = TokenAuthenticator(
        ioDispatcher,
        tokenManager
    )

    @Provides
    @Singleton
    fun provideTokenInterceptor(tokenManager: TokenManager): TokenInterceptor =
        TokenInterceptor(tokenManager)

    @Provides
    @Singleton
    fun provideHttpInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideTokenService(okHttpClient: OkHttpClient): TokenService = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TokenService::class.java)

    @Provides
    @Singleton
    fun provideAirPortsService(
        authenticator: TokenAuthenticator,
        tokenInterceptor: TokenInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): AirportsService = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient
                .Builder()
                .authenticator(authenticator)
                .addInterceptor(tokenInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build(),
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AirportsService::class.java)

    @Provides
    @Singleton
    fun provideFlightsService(
        authenticator: TokenAuthenticator,
        tokenInterceptor: TokenInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): FlightOffersService = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient
                .Builder()
                .authenticator(authenticator)
                .addInterceptor(tokenInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build(),
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FlightOffersService::class.java)
}
