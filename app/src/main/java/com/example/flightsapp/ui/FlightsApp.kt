package com.example.flightsapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.flightsapp.navigation.FlightsNavHost
import com.example.flightsapp.ui.components.FlightsAppBar
import com.example.flightsapp.ui.theme.FlightsAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightsApp(
    modifier: Modifier = Modifier,
    appState: FlightsAppState = rememberNiaAppState()
) {
    val currentScreen = appState.currentScreen
    val isDarkModeOn = isSystemInDarkTheme()
    val appBackgroundDrawable: Int = currentScreen.backgroundDrawable

    SideEffect {
        appState.systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = if (currentScreen.isTopAppBarTransparent) true else !isDarkModeOn
        )
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (appBackgroundDrawable != 0) {
            Image(
                modifier = modifier.fillMaxSize(),
                painter = painterResource(id = appBackgroundDrawable),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                FlightsAppBar(
                    currentScreen = appState.currentScreen,
                    navigateUp = appState.navController::navigateUp
                )
            }
        ) { innerPadding ->
            FlightsNavHost(
                modifier = modifier.padding(innerPadding),
                navController = appState.navController
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FlightsAppPreview() {
    FlightsAppTheme {
        FlightsApp()
    }
}
