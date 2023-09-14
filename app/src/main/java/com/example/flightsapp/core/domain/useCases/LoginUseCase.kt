package com.example.flightsapp.core.domain.useCases

import android.util.Log
import com.example.flightsapp.R
import com.example.flightsapp.core.common.UiText
import com.example.flightsapp.core.common.WorkResult
import com.example.flightsapp.core.data.repositories.auth.AuthenticationRepository
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): WorkResult<*> = try {
        authenticationRepository.login(
            email,
            password
        )
        WorkResult.Success(Unit)
    } catch (e: FirebaseAuthException) {
        WorkResult.Error(UiText.DynamicString(e.message.orEmpty()))
    } catch (e: FirebaseNetworkException) {
        WorkResult.Error(UiText.DynamicString(e.message.orEmpty()))
    } catch (
        @Suppress("TooGenericExceptionCaught")
        e: Exception
    ) {
        Log.e(TAG, "Exception occurred: ${e.message}")
        WorkResult.Error(UiText.ResourceString(R.string.error_something_went_wrong))
    }

    companion object {
        private const val TAG = "LoginUseCase"
    }
}
