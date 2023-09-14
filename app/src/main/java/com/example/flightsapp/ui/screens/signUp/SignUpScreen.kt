package com.example.flightsapp.ui.screens.signUp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flightsapp.R
import com.example.flightsapp.core.common.DevicePreviews
import com.example.flightsapp.core.common.UiText
import com.example.flightsapp.core.common.showToast
import com.example.flightsapp.core.common.ui.LoadingIndicator
import com.example.flightsapp.core.common.ui.TextFieldWithValidation
import com.example.flightsapp.ui.components.BaseCard
import com.example.flightsapp.ui.components.DefaultButton
import com.example.flightsapp.ui.screens.signUp.inputValidation.RegistrationError
import com.example.flightsapp.ui.screens.signUp.model.SignUpState
import com.example.flightsapp.ui.theme.FlightsAppTheme
import com.example.flightsapp.ui.theme.customColorsPalette

@Composable
fun SignUpRoute(
    viewModel: SignUpViewModel = hiltViewModel(),
    onAccountCreated: () -> Unit
) {
    val registerData by viewModel.registerData.collectAsStateWithLifecycle()
    SignUpScreen(
        registerData = registerData,
        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailChange,
        onPhoneChange = viewModel::onPhoneChange,
        onPasswordChange = viewModel::onPasswordChange,
        onCreateClick = viewModel::onCreateClick,
        onAccountCreated = onAccountCreated
    )
}

@Composable
private fun SignUpScreen(
    modifier: Modifier = Modifier,
    registerData: SignUpState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onCreateClick: () -> Unit,
    onAccountCreated: () -> Unit
) {
    val context = LocalContext.current

    if (registerData.registrationComplete) {
        LaunchedEffect(Unit) { onAccountCreated() }
    }

    when (registerData.registrationError) {
        is UiText.DynamicString -> LaunchedEffect(Unit) { context.showToast(registerData.registrationError.value) }
        is UiText.ResourceString -> {
            val error = stringResource(id = registerData.registrationError.resId)
            LaunchedEffect(Unit) { context.showToast(error) }
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .padding(horizontal = 42.dp)
            ) {
                Spacer(modifier = modifier.height(42.dp))

                Text(
                    text = stringResource(id = R.string.register_card_title),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.customColorsPalette.extraTextColor
                )

                Spacer(modifier = modifier.height(16.dp))

                TextFieldWithValidation(
                    label = R.string.register_name_hint,
                    text = registerData.userInput.fullName,
                    onInputChange = onNameChange,
                    isError = registerData.inputError is RegistrationError.NameError,
                    error = registerData.inputError?.errorMessage
                )

                Spacer(modifier = modifier.height(4.dp))

                TextFieldWithValidation(
                    label = R.string.login_email_hint,
                    text = registerData.userInput.email,
                    onInputChange = onEmailChange,
                    isError = registerData.inputError is RegistrationError.EmailError,
                    error = registerData.inputError?.errorMessage

                )

                Spacer(modifier = modifier.height(4.dp))

                TextFieldWithValidation(
                    label = R.string.register_phone_hint,
                    text = registerData.userInput.phone,
                    onInputChange = onPhoneChange,
                    isError = registerData.inputError is RegistrationError.PhoneError,
                    error = registerData.inputError?.errorMessage

                )

                Spacer(modifier = modifier.height(4.dp))

                TextFieldWithValidation(
                    label = R.string.login_password_hint,
                    text = registerData.userInput.password,
                    onInputChange = onPasswordChange,
                    isError = registerData.inputError is RegistrationError.PasswordError,
                    error = registerData.inputError?.errorMessage
                )

                Spacer(modifier = modifier.height(30.dp))

                DefaultButton(
                    modifier = modifier.fillMaxWidth(),
                    enabled = !registerData.isLoading,
                    onClick = onCreateClick
                ) {
                    Text(text = stringResource(id = R.string.register_create_account))
                }

                Spacer(modifier = modifier.height(34.dp))

                Text(
                    text = stringResource(id = R.string.register_have_account)
                )

                TextButton(onClick = {}, content = {
                    Text(
                        text = stringResource(R.string.register_sign_in),
                        color = MaterialTheme.customColorsPalette.extraTextColor,
                        style = MaterialTheme.typography.titleMedium
                    )
                })

                Spacer(modifier = modifier.height(24.dp))
            }
        }
    }

    if (registerData.isLoading) {
        LoadingIndicator()
    }
}

@DevicePreviews
@Composable
private fun DefaultPreview() {
    FlightsAppTheme {
        SignUpScreen(
            registerData = SignUpState(),
            onNameChange = {},
            onEmailChange = {},
            onPhoneChange = {},
            onPasswordChange = {},
            onCreateClick = {},
            onAccountCreated = {}
        )
    }
}
