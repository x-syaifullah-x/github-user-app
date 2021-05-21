package com.dicoding.picodiploma.githubuserapp.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.content.getSystemService
import java.util.*

class DailyReminder {

    companion object {
        const val ID_REPEATING = 281298
        const val ACTION_DAILY_REMINDER = "ACTION_DAILY_REMINDER"
    }


    private fun getBroadcast(context: Context): PendingIntent {
        val intent = Intent(context, DailyReminderReceiver::class.java)
            .apply { action = ACTION_DAILY_REMINDER }
        return PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)
    }

    fun setRepeatingAlarm(context: Context, isActive: Boolean) {
        val alarmManager = context.getSystemService<AlarmManager>() ?: return

        if (isActive) {
            val calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = 1
            calendar[Calendar.MINUTE] = 37
            calendar[Calendar.SECOND] = 0
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                getBroadcast(context)
            )
        } else {
            val pendingIntent = getBroadcast(context)
            pendingIntent.cancel()
            alarmManager.cancel(pendingIntent)
        }
    }
}