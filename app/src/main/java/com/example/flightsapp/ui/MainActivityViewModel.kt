package com.example.flightsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsapp.core.data.repositories.userPreferences.UserPreferencesRepository
import com.example.flightsapp.core.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    userPreferencesRepository: UserPreferencesRepository
) :
    ViewModel() {

    val userPreference: StateFlow<AppState> = userPreferencesRepository.getPreferences()
        .map { userData ->
            AppState.Success(
                userPreferences = UserData(
                    darkThemeConfig = userData.darkThemeConfig,
                    dynamicColors = userData.dynamicColors
                ),
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppState.Loading
        )
}

sealed interface AppState {
    object Loading : AppState
    data class Success(val userPreferences: UserData) : AppState
}
