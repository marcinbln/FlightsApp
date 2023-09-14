package com.example.flightsapp.core.data.repositories.userPreferences.remote

import com.example.flightsapp.core.model.UserData
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

const val DARK_MODE = "darkMode"
const val LANGUAGE = "language"
const val USER_SETTINGS_COLLECTION = "userSettings"

class RemoteDataSourceImpl @Inject constructor() : RemoteDataSource {

    private val db = Firebase.firestore

    override suspend fun saveSettings(userId: String, language: String, darkMode: Boolean) {
        val userSettings = hashMapOf(
            DARK_MODE to darkMode,
            LANGUAGE to language
        )

        db.collection(USER_SETTINGS_COLLECTION).document(userId).set(userSettings).await()
    }

    override suspend fun getSettings(userId: String): UserData? =
        db.collection(USER_SETTINGS_COLLECTION).document(userId).get().await()
            .toObject<UserData>()

    override suspend fun getLanguage(userId: String): String? =
        db.collection(USER_SETTINGS_COLLECTION).document(userId).get().await()
            .toObject<UserData>()?.language

    override suspend fun getDarkMode(userId: String): Boolean? =
        db.collection(USER_SETTINGS_COLLECTION).document(userId).get().await()
            .toObject<UserData>()?.darkMode

    override suspend fun saveLanguage(userId: String, languageSetting: String) {
        val language = hashMapOf(
            LANGUAGE to languageSetting,
        )
        db.collection(USER_SETTINGS_COLLECTION).document(userId).set(
            language,
            SetOptions.merge()
        ).await()
    }

    override suspend fun saveDarkMode(userId: String, darkModeSetting: Boolean) {
        val darkMode = hashMapOf(
            DARK_MODE to darkModeSetting,
        )
        db.collection(USER_SETTINGS_COLLECTION).document(userId).set(
            darkMode,
            SetOptions.merge()
        ).await()
    }
}
