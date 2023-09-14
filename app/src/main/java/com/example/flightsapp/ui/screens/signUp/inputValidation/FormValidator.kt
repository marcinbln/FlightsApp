package com.example.flightsapp.ui.screens.signUp.inputValidation

object FormValidator {
    private const val PASSWORD_MIN_LENGTH = 10
    private const val PHONE_MIN_LENGTH = 12

    private val emailRegex = Regex(
        pattern = "\\S+@(([(a-z\\d-])+|(\\d]+))\\.(([a-z\\d]{2,})|(\\d+))",
        options = setOf(RegexOption.IGNORE_CASE)
    )

    val passwordRequirements = listOf(
        ::isLongEnough,
        ::containsUppercase,
        ::containsSpecialChar,
        ::containsDigit
    )

    fun validateName(fullName: String): Boolean = fullName.isNotBlank()

    fun validateEmail(email: String): Boolean = emailRegex.matches(email)

    fun validatePhone(phone: String): Boolean =
        phone.startsWith("+") && phone.length == PHONE_MIN_LENGTH

    private fun isLongEnough(password: String): ValidationResult =
        if (password.length >= PASSWORD_MIN_LENGTH) {
            ValidationResult.Valid
        } else {
            ValidationResult.Invalid(RegistrationError.PasswordError.PasswordErrorLength)
        }

    private fun containsUppercase(password: String): ValidationResult =
        if (password.any { it.isUpperCase() }) {
            ValidationResult.Valid
        } else {
            ValidationResult.Invalid(RegistrationError.PasswordError.PasswordErrorUppercase)
        }

    private fun containsSpecialChar(password: String) = if (password.any { ".#!?".contains(it) }) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid(RegistrationError.PasswordError.PasswordErrorSpecialChar)
    }

    private fun containsDigit(password: String) = if (password.any { it.isDigit() }) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid(RegistrationError.PasswordError.PasswordErrorDigit)
    }

    sealed interface ValidationResult {
        data class Invalid(val error: RegistrationError) : ValidationResult
        object Valid : ValidationResult
    }
}
