package com.example.flightsapp.core.data.source.network.token

interface TokenManager {
    fun getToken(): String?
    suspend fun refreshToken()
}
