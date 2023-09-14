package com.example.flightsapp.core.data.source.remote

import com.example.flightsapp.core.model.DarkThemeConfig

data class UserDataFirestore(
    val darkThemeConfig: String = DarkThemeConfig.FOLLOW_SYSTEM.name
)
