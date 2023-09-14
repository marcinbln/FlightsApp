package com.example.flightsapp.ui.screens.loadingScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsapp.core.data.repositories.auth.AuthenticationRepository
import com.example.flightsapp.core.data.repositories.userPreferences.local.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightsAppViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _loadingState =
        MutableStateFlow<LoadingState>(LoadingState.Loading)
    val loadingState: MutableStateFlow<LoadingState> = _loadingState

    init {
        syncUserSettings()
    }

    private fun syncUserSettings() {
        viewModelScope.launch {
            if (authenticationRepository.isLoggedIn.first()) {
                userPreferencesRepository.syncPreferences()
                _loadingState.value = LoadingState.SignedIn
            } else {
                _loadingState.value = LoadingState.SignedOut
            }
        }
    }
}

sealed interface LoadingState {
    object SignedOut : LoadingState
    object Loading : LoadingState
    object SignedIn : LoadingState
}
