package com.example.flightsapp.core.domain.useCases

import com.example.flightsapp.core.common.AppResult
import com.example.flightsapp.core.common.UiText
import com.example.flightsapp.core.data.repositories.auth.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuthException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): AppResult<*> = try {
        authenticationRepository.login(
            email,
            password
        )
        AppResult.Success(Unit)
    } catch (e: FirebaseAuthException) {
        AppResult.Error(UiText.DynamicString(e.message.orEmpty()))
    }
}
