package com.example.flightsapp.core.data.repositories.userPreferences.local

import com.example.flightsapp.dataStore.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    fun getPreferences(): Flow<UserPreferences>
    suspend fun savePreferences(language: String, darkMode: Boolean)
    suspend fun saveDarkMode(userId: String, darkModeSetting: Boolean)
    suspend fun saveLanguage(userId: String, languageSetting: String)
    fun getDarkMode(): Flow<Boolean>
    fun getLanguage(): Flow<String>
    suspend fun syncPreferences()
}
