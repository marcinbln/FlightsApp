package com.example.flightsapp.ui.screens.signIn

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flightsapp.R
import com.example.flightsapp.core.common.DevicePreviews
import com.example.flightsapp.core.common.UiText
import com.example.flightsapp.core.common.showToast
import com.example.flightsapp.core.common.ui.LoadingIndicator
import com.example.flightsapp.core.common.ui.TextFieldWithValidation
import com.example.flightsapp.ui.components.BaseCard
import com.example.flightsapp.ui.components.DefaultButton
import com.example.flightsapp.ui.screens.signIn.model.SignInState
import com.example.flightsapp.ui.theme.Blue800

@Composable
fun SignInRoute(
    signInViewModel: SignInViewModel = hiltViewModel(),
    onLoggedIn: () -> Unit
) {
    val signInState by signInViewModel.signInState.collectAsState()
    SignInScreen(
        signInState = signInState,
        onEmailChange = signInViewModel::onEmailChange,
        onPasswordChange = signInViewModel::onPasswordChange,
        onSignInClick = signInViewModel::onSignIn,
        onLoggedIn = onLoggedIn,
        resetError = signInViewModel::resetError
    )
}

@Composable
private fun SignInScreen(
    modifier: Modifier = Modifier,
    signInState: SignInState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onLoggedIn: () -> Unit,
    resetError: () -> Unit
) {
    val context = LocalContext.current

    if (signInState.registrationComplete) {
        LaunchedEffect(Unit) { onLoggedIn() }
    }

    when (signInState.registerError) {
        is UiText.DynamicString -> {
            context.showToast(signInState.registerError.value)
            resetError()
        }

        is UiText.ResourceString -> {
            val error = stringResource(id = signInState.registerError.resId)
            context.showToast(error)
            resetError()
        }

        null -> Unit
    }

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        BaseCard {
            Spacer(modifier = modifier.height(72.dp))

            Text(
                text = stringResource(id = R.string.login_card_title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimaryContainer else Blue800
            )

            Spacer(modifier = modifier.height(66.dp))

            TextFieldWithValidation(
                label = R.string.login_email_hint,
                text = signInState.email,
                onInputChange = onEmailChange,
                isError = signInState.inputError is LoginError.EmptyEmailError,
                error = signInState.inputError?.errorMessage
            )

            TextFieldWithValidation(
                label = R.string.login_password_hint,
                text = signInState.password,
                onInputChange = onPasswordChange,
                isError = signInState.inputError is LoginError.EmptyPasswordError,
                error = signInState.inputError?.errorMessage
            )

            Spacer(modifier = modifier.height(24.dp))

            DefaultButton(
                modifier = modifier.fillMaxWidth(),
                enabled = !signInState.isLoading,
                onClick = onSignInClick
            ) {
                Text(text = stringResource(id = R.string.login_sign_in_btn))
            }

            Spacer(modifier = modifier.height(24.dp))

            Text(text = stringResource(R.string.login_forgot_password_button))

            Spacer(modifier = modifier.height(40.dp))

            Text(text = stringResource(R.string.login_no_account))

            Spacer(modifier = modifier.height(12.dp))

            Text(
                text = stringResource(R.string.login_create_account),
                color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimaryContainer else Blue800

            )

            Spacer(modifier = modifier.height(24.dp))
        }
    }

    if (signInState.isLoading) {
        LoadingIndicator()
    }
}

@DevicePreviews
@Composable
private fun SignInPreview() {
    SignInScreen(
        signInState = SignInState(),
        onPasswordChange = {},
        onEmailChange = {},
        onSignInClick = {},
        onLoggedIn = {},
        resetError = {}
    )
}
