package com.example.flightsapp.core.data.source.remote

import com.example.flightsapp.core.data.repositories.auth.AuthenticationRepository
import com.example.flightsapp.core.model.DarkThemeConfig
import com.example.flightsapp.core.model.UserData
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SettingsRemoteDataSource @Inject constructor(
    val authenticationRepository: AuthenticationRepository
) : SettingsDataSource {

    private val db = Firebase.firestore
    private val userId = authenticationRepository.userId

    override suspend fun saveSettings(darkMode: Boolean) {
        val userSettings = hashMapOf(
            DARK_MODE to darkMode
        )

        userId?.let {
            db.collection(USER_SETTINGS_COLLECTION)
                .document(it)
                .set(userSettings)
                .await()
        }
    }

    override suspend fun getSettings(): UserData? {
        return userId?.let {
            db.collection(USER_SETTINGS_COLLECTION)
                .document(it)
                .get()
                .await()
                .toObject(UserDataFirestore::class.java)
                .toUserData()
        }
    }

    override suspend fun getDarkMode(): DarkThemeConfig? = userId?.let {
        db.collection(USER_SETTINGS_COLLECTION)
            .document(it)
            .get()
            .await()
            .toObject(UserData::class.java)?.darkThemeConfig
    }

    override suspend fun saveDarkMode(darkModeSetting: DarkThemeConfig) {
        val darkMode = UserDataFirestore(
            darkThemeConfig = when (darkModeSetting) {
                DarkThemeConfig.FOLLOW_SYSTEM -> DarkThemeConfig.FOLLOW_SYSTEM.name
                DarkThemeConfig.LIGHT -> DarkThemeConfig.LIGHT.name
                DarkThemeConfig.DARK -> DarkThemeConfig.DARK.name
            }
        )

        userId?.let {
            db.collection(USER_SETTINGS_COLLECTION)
                .document(it)
                .set(
                    darkMode,
                    SetOptions.merge()
                )
                .await()
        }
    }

    companion object {
        private const val DARK_MODE = "darkMode"
        private const val USER_SETTINGS_COLLECTION = "userSettings"
    }
}

private fun UserDataFirestore?.toUserData(): UserData = UserData(
    darkThemeConfig = when (this?.darkThemeConfig) {
        "FOLLOW_SYSTEM" -> DarkThemeConfig.FOLLOW_SYSTEM
        "LIGHT" -> DarkThemeConfig.LIGHT
        "DARK" -> DarkThemeConfig.DARK
        else -> DarkThemeConfig.FOLLOW_SYSTEM
    },
    dynamicColors = true // todo
)
