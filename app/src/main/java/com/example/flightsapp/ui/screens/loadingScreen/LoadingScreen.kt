package com.example.flightsapp.ui.screens.loadingScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flightsapp.core.common.DevicePreviews
import com.example.flightsapp.core.common.ui.LoadingIndicator

@Composable
fun LoadingRoute(
    splashViewModel: FlightsAppViewModel = hiltViewModel(),
    onSignedIn: () -> Unit,
    onSignedOut: () -> Unit

) {
    val loadingState by splashViewModel.loadingState.collectAsStateWithLifecycle()
    LoadingScreen(
        loadingState = loadingState,
        onSignedIn = onSignedIn,
        onSignedOut = onSignedOut
    )
}

@Composable
private fun LoadingScreen(
    loadingState: LoadingState,
    onSignedIn: () -> Unit,
    onSignedOut: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (loadingState) {
            LoadingState.Loading -> LoadingIndicator()
            LoadingState.SignedIn -> LaunchedEffect(Unit) { onSignedIn() }
            LoadingState.SignedOut -> LaunchedEffect(Unit) { onSignedOut() }
        }
    }
}

@DevicePreviews
@Composable
private fun LoadingPreview() {
    LoadingScreen(
        loadingState = LoadingState.Loading,
        onSignedIn = {},
        onSignedOut = {}
    )
}
