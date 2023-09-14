package com.example.flightsapp.ui.screens.signIn.model

import com.example.flightsapp.core.common.UiText
import com.example.flightsapp.ui.screens.signIn.LoginError

data class SignInState(
    val isLoading: Boolean = false,
    val registerError: UiText? = null,
    val registrationComplete: Boolean = false,
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val password: String = "",
    val inputError: LoginError? = null
)
