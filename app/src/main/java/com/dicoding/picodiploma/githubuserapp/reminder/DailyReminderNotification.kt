package com.dicoding.picodiploma.githubuserapp.reminder

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.content.Context
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class DailyReminderNotification(context: Context) {

    companion object {
        const val CHANNEL_ID_DAILY_REMINDER = "CHANNEL_ID_DAILY_REMINDER"
        const val CHANNEL_NAME_DAILY_REMINDER = "Daily Reminder"
        private const val GROUP_ID_DAILY_REMINDER = "GROUP_ID_DAILY_REMINDER"
        private const val GROUP_NAME_DAILY_REMINDER = "Daily Reminder"
    }

    private val notificationManager = NotificationManagerCompat.from(context)

    init {
        val ncg = notificationManager.notificationChannelGroups
        if (!ncg.contains(notificationManager.getNotificationChannelGroup(GROUP_ID_DAILY_REMINDER)))
            if (VERSION.SDK_INT >= VERSION_CODES.O)
                notificationManager.createNotificationChannelGroup(
                    NotificationChannelGroup(GROUP_ID_DAILY_REMINDER, GROUP_NAME_DAILY_REMINDER)
                )
    }

    fun showNotification(id: Int, channelName: String?, notification: NotificationCompat.Builder) {
        if (VERSION.SDK_INT >= VERSION_CODES.O) {
            notification.setChannelId(channelName!!)
            notificationManager.createNotificationChannel(
                NotificationChannel(channelName, channelName, IMPORTANCE_DEFAULT)
            )
        }
        notificationManager.notify(id, notification.build())
    }
}
