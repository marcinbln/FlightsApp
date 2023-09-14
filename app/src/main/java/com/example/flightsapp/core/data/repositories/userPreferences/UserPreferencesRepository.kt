package com.example.flightsapp.core.data.repositories.userPreferences

import com.example.flightsapp.core.model.DarkThemeConfig
import com.example.flightsapp.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    fun getPreferences(): Flow<UserData>
    suspend fun saveDarkMode(darkModeSetting: DarkThemeConfig)
    suspend fun syncPreferences()
    suspend fun saveDynamicColors(enabled: Boolean)
}
