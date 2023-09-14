package com.example.flightsapp.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.flightsapp.R
import com.example.flightsapp.core.common.DevicePreviews
import com.example.flightsapp.ui.screens.splash.components.SplashCard
import com.example.flightsapp.ui.theme.FlightsAppTheme

@Composable
fun SplashRoute(
    onSignInClicked: () -> Unit,
    onSignUpClicked: () -> Unit
) {
    SplashScreen(
        onSignInClicked = onSignInClicked,
        onSignUpClicked = onSignUpClicked
    )
}

@Composable
private fun SplashScreen(
    modifier: Modifier = Modifier,
    onSignInClicked: () -> Unit,
    onSignUpClicked: () -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Image(
            modifier = modifier
                .widthIn(max = 640.dp)
                .fillMaxWidth()
                .padding(42.dp)
                .weight(1f),
            contentScale = ContentScale.Fit,
            painter = painterResource(id = R.drawable.plane),
            contentDescription = null
        )

        SplashCard(
            onSignUpClicked = onSignUpClicked,
            onSignInClicked = onSignInClicked
        )
    }
}

@DevicePreviews
@Composable
private fun SplashPreview() {
    FlightsAppTheme {
        SplashScreen(
            onSignInClicked = {},
            onSignUpClicked = {}
        )
    }
}
