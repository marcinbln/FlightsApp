package com.example.flightsapp.ui.screens.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsapp.R
import com.example.flightsapp.core.common.WorkResult
import com.example.flightsapp.core.common.errors.ErrorStringRes
import com.example.flightsapp.core.domain.useCases.LoginUseCase
import com.example.flightsapp.ui.screens.signIn.model.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _signInState = MutableStateFlow(SignInState())
    val signInState = _signInState.asStateFlow()

    fun onEmailChange(email: String) {
        _signInState.update {
            it.copy(email = email.trim())
        }
    }

    fun onPasswordChange(password: String) {
        _signInState.update {
            it.copy(password = password.trim())
        }
    }

    fun onSignIn() {
        if (validateInput()) {
            viewModelScope.launch {
                _signInState.update { it.copy(isLoading = true) }

                when (
                    val result =
                        loginUseCase(signInState.value.email, signInState.value.password)
                ) {
                    is WorkResult.Error -> _signInState.update { it.copy(registerError = result.error) }
                    is WorkResult.Success -> _signInState.update { it.copy(registrationComplete = true) }
                }

                _signInState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun validateInput(): Boolean {
        return if (signInState.value.email.isBlank()) {
            _signInState.update {
                it.copy(inputError = LoginError.EmptyEmailError)
            }
            false
        } else if (signInState.value.password.isBlank()) {
            _signInState.update {
                it.copy(inputError = LoginError.EmptyPasswordError)
            }
            false
        } else {
            true
        }
    }

    fun resetError() {
        _signInState.update {
            it.copy(registerError = null)
        }
    }
}

sealed class LoginError(errorMessage: Int) : ErrorStringRes(errorMessage) {
    object EmptyEmailError : LoginError(R.string.input_error_empty_name)
    object EmptyPasswordError : LoginError(R.string.input_error_empty_name)
}
