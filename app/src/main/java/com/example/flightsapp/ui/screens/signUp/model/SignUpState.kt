package com.example.flightsapp.ui.screens.signUp.model

import androidx.compose.runtime.Stable
import com.example.flightsapp.core.common.UiText
import com.example.flightsapp.ui.screens.signUp.inputValidation.RegistrationError

@Stable
data class SignUpState(
    val isLoading: Boolean = false,
    val registrationError: UiText? = null,
    val registrationComplete: Boolean = false,
    val userInput: UserInput = UserInput(),
    val inputError: RegistrationError? = null
) {
    data class UserInput(
        val fullName: String = "",
        val email: String = "",
        val phone: String = "",
        val password: String = ""
    )
}
