package com.example.flightsapp.core.data.repositories.userPreferences.local

import androidx.datastore.core.DataStore
import com.example.flightsapp.core.data.repositories.auth.AuthenticationRepository
import com.example.flightsapp.core.data.repositories.userPreferences.remote.RemoteDataSource
import com.example.flightsapp.dataStore.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val userPreferencesStore: DataStore<UserPreferences>,
    private val authenticationRepository: AuthenticationRepository,
    private val remoteDataSource: RemoteDataSource
) : UserPreferencesRepository {

    override fun getPreferences(): Flow<UserPreferences> =
        userPreferencesStore.data.catch { exception ->
            if (exception is Exception) {
                emit(UserPreferences.getDefaultInstance())
            }
        }

    override suspend fun savePreferences(language: String, darkMode: Boolean) {
        userPreferencesStore.updateData { currentPreferences ->
            currentPreferences.toBuilder().setLanguage(language).setDarkMode(darkMode).build()
        }
    }

    override fun getDarkMode(): Flow<Boolean> = userPreferencesStore.data.map { it.darkMode }

    override fun getLanguage(): Flow<String> = userPreferencesStore.data.map { it.language }

    override suspend fun syncPreferences() {
        val currentUserId = authenticationRepository.getCurrentUserId()

        if (currentUserId != null) {
            with(remoteDataSource.getSettings(currentUserId)) {
                saveLanguage(currentUserId, this?.language ?: Locale.getDefault().toLanguageTag())
                saveDarkMode(currentUserId, this?.darkMode ?: false)
            }
        }
    }

    override suspend fun saveDarkMode(userId: String, darkModeSetting: Boolean) {
        remoteDataSource.saveDarkMode(userId, darkModeSetting)
        userPreferencesStore.updateData { currentPreferences ->
            currentPreferences.toBuilder().setDarkMode(darkModeSetting).build()
        }
    }

    override suspend fun saveLanguage(userId: String, languageSetting: String) {
        remoteDataSource.saveLanguage(userId, languageSetting)
        userPreferencesStore.updateData { currentPreferences ->
            currentPreferences.toBuilder().setLanguage(languageSetting).build()
        }
    }
}
