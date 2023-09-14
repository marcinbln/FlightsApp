package com.example.flightsapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flightsapp.core.data.repositories.auth.AuthenticationRepository
import com.example.flightsapp.navigation.FlightsNavHost
import com.example.flightsapp.navigation.Screen
import com.example.flightsapp.ui.components.FlightsAppBar

@Composable
fun FlightsApp(
    authenticationRepository: AuthenticationRepository,
    appState: FlightsAppState = rememberNiaAppState(
        authenticationRepository = authenticationRepository
    )
) {
    val isLoggedOut by appState.isLoggedIn.collectAsStateWithLifecycle()
    LaunchedEffect(isLoggedOut) {
        if (isLoggedOut) {
            appState.navController.navigate(Screen.SPLASH.route)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = appState.currentScreen.value.backgroundDrawable),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }

    Scaffold(containerColor = Color.Transparent, topBar = {
        FlightsAppBar(
            currentScreen = appState.currentScreen.value,
            navigateUp = appState.navController::navigateUp
        )
    }) { innerPadding ->
        FlightsNavHost(
            modifier = Modifier.padding(innerPadding),
            flightsAppState = appState
        )
    }
}
