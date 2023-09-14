package com.example.flightsapp.core.data.repositories.auth

import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    suspend fun login(email: String, password: String)
    suspend fun register(email: String, password: String)
    fun logout()
    val isLoggedIn: Flow<Boolean>
    val userId: String?
}
