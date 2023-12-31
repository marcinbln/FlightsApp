package com.example.flightsapp.ui.screens.splash.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.flightsapp.R
import com.example.flightsapp.core.common.DevicePreviews
import com.example.flightsapp.ui.components.BaseCard
import com.example.flightsapp.ui.components.DefaultButton
import com.example.flightsapp.ui.components.OutlinedButton
import com.example.flightsapp.ui.theme.FlightsAppTheme
import com.example.flightsapp.ui.theme.customColorsPalette

@Composable
internal fun SplashCard(
    modifier: Modifier = Modifier,
    onSignInClicked: () -> Unit,
    onSignUpClicked: () -> Unit
) {
    BaseCard {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(horizontal = 42.dp)
        ) {
            Spacer(modifier = modifier.height(72.dp))

            Text(
                text = stringResource(id = R.string.splash_card_splash_title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.customColorsPalette.extraTextColor
            )

            Spacer(modifier = modifier.height(66.dp))

            OutlinedButton(
                modifier = modifier.fillMaxWidth(),
                onClick = onSignInClicked
            ) {
                Text(text = stringResource(id = R.string.splash_sign_in))
            }

            Spacer(modifier = modifier.height(10.dp))

            DefaultButton(
                modifier.fillMaxWidth(),
                onClick = onSignUpClicked
            ) {
                Text(text = stringResource(id = R.string.splash_sign_up))
            }

            Spacer(modifier = modifier.height(34.dp))
        }
    }
}

@DevicePreviews
@Composable
private fun SplashCardPreview() {
    FlightsAppTheme {
        SplashCard(
            onSignUpClicked = {},
            onSignInClicked = {}
        )
    }
}
