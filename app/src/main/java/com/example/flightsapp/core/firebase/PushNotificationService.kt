package com.example.flightsapp.core.firebase

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.flightsapp.MainActivity
import com.example.flightsapp.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class PushNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntentFlag = PendingIntent.FLAG_IMMUTABLE
        val pendingIntent = PendingIntent.getActivity(this, Random.nextInt(), intent, pendingIntentFlag)

        val channelId = when (message.from) {
            UPDATES_TOPIC -> FLIGHT_UPDATES_CHANNEL_ID
            DEALS_TOPIC -> DEALS_CHANNEL_ID
            else -> OTHERS_CHANNEL_ID
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setSmallIcon(R.drawable.ic_notifications)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        notificationManager.notify(Random.nextInt(), notificationBuilder.build())
    }

    @Suppress("RedundantOverride")
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    companion object {
        private const val UPDATES_TOPIC = "/topics/updates"
        private const val DEALS_TOPIC = "/topics/deals"
        const val FLIGHT_UPDATES_CHANNEL_ID = "flight_updates_channel_ID"
        const val FLIGHT_UPDATES_CHANNEL_NAME = "Flight updates"
        const val DEALS_CHANNEL_ID = "deals_channel_ID"
        const val DEALS_UPDATES_CHANNEL_NAME = "Deals & discounts"
        const val OTHERS_CHANNEL_ID = "others_channel_ID"
    }
}
