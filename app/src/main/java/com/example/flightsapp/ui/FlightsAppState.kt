package com.example.flightsapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.flightsapp.navigation.Screen
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun rememberNiaAppState(
    navController: NavHostController = rememberNavController(),
    systemUiController: SystemUiController = rememberSystemUiController()
): FlightsAppState {
    return remember(navController, systemUiController) {
        FlightsAppState(navController, systemUiController)
    }
}

@Stable
class FlightsAppState(
    val navController: NavHostController,
    val systemUiController: SystemUiController
) {
    private val currentRoute: String?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route

    val currentScreen: Screen
        @Composable get() = Screen
            .values()
            .find { it.route == currentRoute } ?: Screen.SPLASH
}
