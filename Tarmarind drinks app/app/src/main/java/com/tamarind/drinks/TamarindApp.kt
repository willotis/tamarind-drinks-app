package com.tamarind.drinks

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TamarindApp : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NotificationManager::class.java)

            // Orders channel
            val ordersChannel = NotificationChannel(
                CHANNEL_ID_ORDERS,
                "Order Updates",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications about order status updates"
                enableVibration(true)
            }

            // Promotions channel
            val promotionsChannel = NotificationChannel(
                CHANNEL_ID_PROMOTIONS,
                "Promotions & Offers",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Promotional offers and new product alerts"
            }

            notificationManager?.createNotificationChannel(ordersChannel)
            notificationManager?.createNotificationChannel(promotionsChannel)
        }
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()

    companion object {
        const val CHANNEL_ID_ORDERS = "tamarind_orders"
        const val CHANNEL_ID_PROMOTIONS = "tamarind_promotions"
    }
}
