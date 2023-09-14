package com.example.flightsapp.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsapp.core.data.repositories.auth.AuthenticationRepository
import com.example.flightsapp.core.data.repositories.userPreferences.UserPreferencesRepository
import com.example.flightsapp.core.model.DarkThemeConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    val userPreferencesRepository: UserPreferencesRepository
) :
    ViewModel() {

    val settingsUiState: StateFlow<SettingsUiState> =
        userPreferencesRepository
            .getPreferences()
            .map { userData ->
                SettingsUiState.Success(
                    settings = UserEditableSettings(
                        darkThemeConfig = userData.darkThemeConfig,
                        dynamicColors = userData.dynamicColors
                    ),
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SettingsUiState.Loading,
            )

    fun onSignOutClick() {
        authenticationRepository.logout()
    }

    fun onDarkModeSelect(s: DarkThemeConfig) {
        viewModelScope.launch { userPreferencesRepository.saveDarkMode(s) }
    }

    fun dynamicColorsSwitched() {
        viewModelScope.launch {
            if (settingsUiState.value is SettingsUiState.Success) {
                val switch =
                    (settingsUiState.value as SettingsUiState.Success).settings.dynamicColors
                val switch2 = !switch
                userPreferencesRepository.saveDynamicColors(switch2)
            }
        }
    }
}

data class UserEditableSettings(
    val darkThemeConfig: DarkThemeConfig,
    val dynamicColors: Boolean
)

sealed interface SettingsUiState {
    object Loading : SettingsUiState
    data class Success(val settings: UserEditableSettings) : SettingsUiState
}
