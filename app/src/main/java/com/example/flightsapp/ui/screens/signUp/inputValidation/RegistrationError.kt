package com.example.flightsapp.ui.screens.signUp.inputValidation

import androidx.annotation.StringRes
import com.example.flightsapp.R
import com.example.flightsapp.core.common.errors.ErrorStringRes

sealed class RegistrationError(errorMessage: Int) : ErrorStringRes(errorMessage) {
    object NameError : RegistrationError(R.string.input_error_empty_name)
    object EmailError : RegistrationError(R.string.input_error_email)
    object PhoneError : RegistrationError(R.string.input_error_phone)

    sealed class PasswordError(@StringRes errorMessage: Int) : RegistrationError(errorMessage) {
        object PasswordErrorLength : PasswordError(R.string.input_error_pass_length)
        object PasswordErrorUppercase : PasswordError(R.string.input_error_pass_uppercase)
        object PasswordErrorSpecialChar : PasswordError(R.string.input_error_pass_special_char)
        object PasswordErrorDigit : PasswordError(R.string.input_error_pass_digit)
    }
}
