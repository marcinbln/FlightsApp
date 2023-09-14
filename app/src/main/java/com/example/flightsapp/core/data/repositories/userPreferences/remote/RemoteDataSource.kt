package com.example.flightsapp.core.data.repositories.userPreferences.remote

import com.example.flightsapp.core.model.UserData

interface RemoteDataSource {
    suspend fun getSettings(userId: String): UserData?
    suspend fun saveSettings(userId: String, language: String, darkMode: Boolean)
    suspend fun getLanguage(userId: String): String?
    suspend fun saveLanguage(userId: String, languageSetting: String)
    suspend fun getDarkMode(userId: String): Boolean?
    suspend fun saveDarkMode(userId: String, darkModeSetting: Boolean)
}
