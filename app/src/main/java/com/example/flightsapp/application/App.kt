package com.example.flightsapp.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.example.flightsapp.core.firebase.PushNotificationService.Companion.DEALS_CHANNEL_ID
import com.example.flightsapp.core.firebase.PushNotificationService.Companion.DEALS_UPDATES_CHANNEL_NAME
import com.example.flightsapp.core.firebase.PushNotificationService.Companion.FLIGHT_UPDATES_CHANNEL_ID
import com.example.flightsapp.core.firebase.PushNotificationService.Companion.FLIGHT_UPDATES_CHANNEL_NAME
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val updatesChannel = NotificationChannel(
                FLIGHT_UPDATES_CHANNEL_ID,
                FLIGHT_UPDATES_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )

            val dealsChannel = NotificationChannel(
                DEALS_CHANNEL_ID,
                DEALS_UPDATES_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )

            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            manager.createNotificationChannels(listOf(updatesChannel, dealsChannel))

            Firebase.messaging.subscribeToTopic("deals")
            Firebase.messaging.subscribeToTopic("updates")
        }
    }
}
