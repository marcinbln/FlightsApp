package com.example.flightsapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.flightsapp.core.data.repositories.auth.AuthenticationRepository
import com.example.flightsapp.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberNiaAppState(
    navController: NavHostController = rememberNavController(),
    authenticationRepository: AuthenticationRepository,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): FlightsAppState {
    return remember(
        navController,
        authenticationRepository,
        coroutineScope
    ) {
        FlightsAppState(
            navController,
            authenticationRepository,
            coroutineScope
        )
    }
}

@Stable
class FlightsAppState(
    val navController: NavHostController,
    val authenticationRepository: AuthenticationRepository,
    coroutineScope: CoroutineScope,
) {
    val currentScreen = mutableStateOf(Screen.SPLASH)

    val isLoggedIn = authenticationRepository.isLoggedIn
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    init {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentScreen.value = Screen
                .values()
                .find { it.route == destination.route } ?: Screen.SPLASH
        }
    }
}
