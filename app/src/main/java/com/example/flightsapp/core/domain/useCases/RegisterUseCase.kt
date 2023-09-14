package com.example.flightsapp.core.domain.useCases

import android.util.Log
import com.example.flightsapp.R
import com.example.flightsapp.core.common.UiText
import com.example.flightsapp.core.common.WorkResult
import com.example.flightsapp.core.data.repositories.auth.AuthenticationRepository
import com.example.flightsapp.core.data.repositories.userPreferences.UserPreferencesRepository
import com.example.flightsapp.core.data.repositories.userRepository.UserRepository
import com.example.flightsapp.core.model.DarkThemeConfig
import com.google.firebase.FirebaseException
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
        authenticationRepository.register(email, password)
        val currentUserId = authenticationRepository.userId
        if (currentUserId != null) {
            userRepository.saveUser(
                currentUserId,
                fullName,
                email,
                phone
            )

            initUserSettings()
        } else {
            WorkResult.Error(UiText.ResourceString(R.string.error_something_went_wrong))
        }

        WorkResult.Success(Unit)
    } catch (e: FirebaseException) {
        WorkResult.Error(UiText.DynamicString(e.message.orEmpty()))
    } catch (
        @Suppress("TooGenericExceptionCaught")
        e: Exception
    ) {
        Log.e("An exception occurred: ", "${e.javaClass.simpleName} - ${e.message}")
        WorkResult.Error(UiText.ResourceString(R.string.error_something_went_wrong))
    }

    private suspend fun initUserSettings() {
        userPreferencesRepository.saveDarkMode(DarkThemeConfig.FOLLOW_SYSTEM)
    }
}
