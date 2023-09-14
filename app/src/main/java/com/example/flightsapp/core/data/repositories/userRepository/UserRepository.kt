package com.example.flightsapp.core.data.repositories.userRepository

interface UserRepository {
    suspend fun saveUserInfo(userId: String, fullName: String, email: String, phone: String)
}
