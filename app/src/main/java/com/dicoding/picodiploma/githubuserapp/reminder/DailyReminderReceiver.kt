package com.dicoding.picodiploma.githubuserapp.reminder

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.dicoding.picodiploma.githubuserapp.R
import com.dicoding.picodiploma.githubuserapp.reminder.DailyReminderNotification.Companion.CHANNEL_ID_DAILY_REMINDER
import com.dicoding.picodiploma.githubuserapp.reminder.DailyReminderNotification.Companion.CHANNEL_NAME_DAILY_REMINDER
import com.dicoding.picodiploma.githubuserapp.ui.MainActivity

class DailyReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action ?: return) {
            DailyReminder.ACTION_DAILY_REMINDER ->
                context?.apply { showNotificationDailyReminder(this) }
        }
    }

    private fun showNotificationDailyReminder(context: Context) {
        DailyReminderNotification(context).showNotification(
            78621,
            CHANNEL_NAME_DAILY_REMINDER,
            NotificationCompat.Builder(context, CHANNEL_ID_DAILY_REMINDER)
                .setSmallIcon(R.drawable.ic_setting_daily_reminder)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText("Test Content Text")
                .setGroup(DailyReminder::class.java.name)
                .setAutoCancel(true)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .setContentIntent(
                    PendingIntent.getActivity(
                        context, 0, Intent(context, MainActivity::class.java), 0
                    )
                )
        )
    }
}