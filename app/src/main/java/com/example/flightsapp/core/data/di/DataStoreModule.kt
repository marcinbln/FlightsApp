package com.example.flightsapp.core.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.flightsapp.core.data.repositories.userPreferences.UserPreferencesSerializer
import com.example.flightsapp.dataStore.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    @Provides
    @Singleton
    fun providePreferencesDataStore(
        @ApplicationContext context: Context,
        @Dispatcher(AppDispatchers.IO) ioDispatcher: CoroutineDispatcher,
        userPreferencesSerializer: UserPreferencesSerializer,
    ): DataStore<UserPreferences> = DataStoreFactory.create(
        serializer = userPreferencesSerializer,
        scope = CoroutineScope(ioDispatcher + SupervisorJob())
    ) {
        context.dataStoreFile("user_preferences.pb")
    }
}
