package com.example.flightsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flightsapp.core.data.repositories.auth.AuthenticationRepository
import com.example.flightsapp.core.data.repositories.userPreferences.UserPreferencesRepository
import com.example.flightsapp.core.model.DarkThemeConfig.DARK
import com.example.flightsapp.core.model.DarkThemeConfig.FOLLOW_SYSTEM
import com.example.flightsapp.core.model.DarkThemeConfig.LIGHT
import com.example.flightsapp.ui.AppState
import com.example.flightsapp.ui.FlightsApp
import com.example.flightsapp.ui.MainActivityViewModel
import com.example.flightsapp.ui.theme.FlightsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authenticationRepository: AuthenticationRepository

    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepository

    private var sync = true

    override fun onResume() {
        super.onResume()
        sync = false
    }

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(
            window,
            false
        )

        setContent {
            val userPrefs: AppState by viewModel.userPreference.collectAsStateWithLifecycle()
            val isSystemInDarkMode = isSystemInDarkTheme()
            val darkThemeConfig = remember { mutableStateOf(isSystemInDarkMode) }
            val dynamicColors = remember { mutableStateOf(true) }

            when (val state = userPrefs) {
                is AppState.Success -> {
                    darkThemeConfig.value =
                        when (state.userPreferences.darkThemeConfig) {
                            FOLLOW_SYSTEM -> isSystemInDarkMode
                            LIGHT -> false
                            DARK -> true
                        }
                    dynamicColors.value =
                        state.userPreferences.dynamicColors
                }
                else -> {}
            }

            FlightsAppTheme(
                darkTheme = darkThemeConfig.value,
                dynamicColor = dynamicColors.value
            ) {
                FlightsApp(
                    authenticationRepository = authenticationRepository
                )
            }
        }
    }
}
