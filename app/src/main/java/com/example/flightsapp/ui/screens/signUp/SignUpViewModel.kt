package com.example.flightsapp.ui.screens.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsapp.core.common.AppResult
import com.example.flightsapp.core.domain.useCases.RegisterUseCase
import com.example.flightsapp.ui.screens.signUp.inputValidation.FormValidator
import com.example.flightsapp.ui.screens.signUp.inputValidation.FormValidator.passwordRequirements
import com.example.flightsapp.ui.screens.signUp.inputValidation.FormValidator.validateEmail
import com.example.flightsapp.ui.screens.signUp.inputValidation.FormValidator.validateName
import com.example.flightsapp.ui.screens.signUp.inputValidation.FormValidator.validatePhone
import com.example.flightsapp.ui.screens.signUp.inputValidation.RegistrationError
import com.example.flightsapp.ui.screens.signUp.model.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _registerData = MutableStateFlow(SignUpState())
    val registerData = _registerData.asStateFlow()

    fun onCreateClick() {
        _registerData.update { it.copy(registrationError = null) }
        val data = _registerData.value.userInput
        if (validateInput(data)) {
            register(data)
        }
    }

    private fun validateInput(userInput: SignUpState.UserInput): Boolean {
        val errors = mutableListOf<RegistrationError>()

        if (!validateName(userInput.fullName)) {
            errors.add(RegistrationError.NameError)
        }

        if (!validateEmail(userInput.email)) {
            errors.add(RegistrationError.EmailError)
        }

        if (!validatePhone(userInput.phone)) {
            errors.add(RegistrationError.PhoneError)
        }

        passwordRequirements.forEach {
            val result = it(userInput.password)
            if (result is FormValidator.ValidationResult.Invalid) {
                errors.add(result.error)
            }
        }

        if (errors.isNotEmpty()) {
            _registerData.update { it.copy(inputError = errors.first()) }
            return false
        }

        return true
    }

    private fun register(userInput: SignUpState.UserInput) {
        viewModelScope.launch {
            _registerData.update { it.copy(isLoading = true) }
            when (
                val result = registerUseCase(
                    email = userInput.email,
                    password = userInput.password,
                    fullName = userInput.fullName,
                    phone = userInput.phone
                )
            ) {
                is AppResult.Success -> _registerData.update { it.copy(registrationComplete = true) }
                is AppResult.Error -> _registerData.update { it.copy(registrationError = result.error) }
            }

            _registerData.update { it.copy(isLoading = false) }
        }
    }

    fun onNameChange(name: String) {
        _registerData.update { it.copy(userInput = it.userInput.copy(fullName = name)) }
    }

    fun onEmailChange(email: String) {
        _registerData.update { it.copy(userInput = it.userInput.copy(email = email)) }
    }

    fun onPhoneChange(phone: String) {
        _registerData.update { it.copy(userInput = it.userInput.copy(phone = phone)) }
    }

    fun onPasswordChange(password: String) {
        _registerData.update { it.copy(userInput = it.userInput.copy(password = password)) }
    }
}
