package com.example.flightsapp.core.domain.useCases

import com.example.flightsapp.R
import com.example.flightsapp.core.common.AppResult
import com.example.flightsapp.core.common.UiText
import com.example.flightsapp.core.data.repositories.auth.AuthenticationRepository
import com.example.flightsapp.core.data.repositories.userPreferences.local.UserPreferencesRepository
import com.example.flightsapp.core.data.repositories.userRepository.UserRepository
import com.google.firebase.auth.FirebaseAuthException
import java.util.Locale
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val userRepository: UserRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        fullName: String,
        phone: String
    ) = try {
        authenticationRepository.register(
            email,
            password
        )
        val currentUserId = authenticationRepository.getCurrentUserId()
        if (currentUserId != null) {
            userRepository.saveUserInfo(
                currentUserId,
                fullName,
                email,
                phone
            )

            initUserSettings(currentUserId)
        } else {
            AppResult.Error(UiText.ResourceString(R.string.error_something_went_wrong))
        }

        AppResult.Success(Unit)
    } catch (e: FirebaseAuthException) {
        AppResult.Error(UiText.DynamicString(e.message.orEmpty()))
    }

    private suspend fun initUserSettings(userId: String) {
        userPreferencesRepository.saveLanguage(userId, Locale.getDefault().toLanguageTag())
        userPreferencesRepository.saveDarkMode(userId, false)
    }
}
