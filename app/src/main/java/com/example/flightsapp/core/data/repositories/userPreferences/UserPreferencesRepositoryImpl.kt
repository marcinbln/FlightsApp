package com.example.flightsapp.core.data.repositories.userPreferences

import androidx.datastore.core.DataStore
import com.example.flightsapp.core.data.repositories.auth.AuthenticationRepository
import com.example.flightsapp.core.data.source.remote.SettingsDataSource
import com.example.flightsapp.core.model.DarkThemeConfig
import com.example.flightsapp.core.model.UserData
import com.example.flightsapp.dataStore.DarkThemeConfigProto
import com.example.flightsapp.dataStore.UserPreferences
import com.example.flightsapp.dataStore.copy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val userPreferencesStore: DataStore<UserPreferences>,
    authenticationRepository: AuthenticationRepository,
    private val settingsDataSource: SettingsDataSource
) : UserPreferencesRepository {

    private val userId = authenticationRepository.userId

    override fun getPreferences(): Flow<UserData> = userPreferencesStore.data.map {
        UserData(
            darkThemeConfig = when (it.darkThemeConfig) {
                null,
                DarkThemeConfigProto.DARK_THEME_CONFIG_UNSPECIFIED,
                DarkThemeConfigProto.UNRECOGNIZED,
                DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM -> DarkThemeConfig.FOLLOW_SYSTEM
                DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT -> DarkThemeConfig.LIGHT
                DarkThemeConfigProto.DARK_THEME_CONFIG_DARK -> DarkThemeConfig.DARK
            },
            dynamicColors = it.dynamicColors
        )
    }

    override suspend fun syncPreferences() {
        userId?.let {
            with(settingsDataSource.getSettings()) {
                userPreferencesStore.updateData {
                    it.copy {
                        this.darkThemeConfig = when (this@with?.darkThemeConfig) {
                            DarkThemeConfig.FOLLOW_SYSTEM -> DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM
                            DarkThemeConfig.LIGHT -> DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT
                            DarkThemeConfig.DARK -> DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
                            else -> DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM
                        }
                    }
                }
            }
        }
    }

    override suspend fun saveDynamicColors(enabled: Boolean) {
        userPreferencesStore.updateData {
            it.copy {
                this.dynamicColors = enabled
            }
        }
    }

    override suspend fun saveDarkMode(darkModeSetting: DarkThemeConfig) {
        userId?.let {
            settingsDataSource.saveDarkMode(darkModeSetting)
        }

        userPreferencesStore.updateData {
            it.copy {
                this.darkThemeConfig = when (darkModeSetting) {
                    DarkThemeConfig.FOLLOW_SYSTEM -> DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM
                    DarkThemeConfig.LIGHT -> DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT
                    DarkThemeConfig.DARK -> DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
                }
            }
        }
    }
}
