package com.example.flightsapp.core.data.source.remote

import com.example.flightsapp.core.model.DarkThemeConfig
import com.example.flightsapp.core.model.UserData

interface SettingsDataSource {
    suspend fun getSettings(): UserData?
    suspend fun saveSettings(darkMode: Boolean)
    suspend fun getDarkMode(): DarkThemeConfig?
    suspend fun saveDarkMode(darkModeSetting: DarkThemeConfig)
}
