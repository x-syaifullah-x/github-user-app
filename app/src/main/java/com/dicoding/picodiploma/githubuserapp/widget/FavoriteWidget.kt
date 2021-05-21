package com.dicoding.picodiploma.githubuserapp.widget

import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.PendingIntent.getBroadcast
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import com.dicoding.picodiploma.githubuserapp.R

class FavoriteWidget : AppWidgetProvider() {

    companion object {
        const val EXTRA_ITEM = "com.dicoding.picodiploma.githubuserapp.EXTRA_ITEM"
        const val ACTION = "com.dicoding.picodiploma.githubuserapp.ACTION"

        private const val ACTION_UPDATE_WIDGET = "ACTION_UPDATE_WIDGET"

        fun updateWidget(context: Context?) {
            context?.run {
                val intent = Intent(this, FavoriteWidget::class.java)
                    .apply { action = ACTION_UPDATE_WIDGET }
                sendBroadcast(intent)
            }
        }
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        appWidgetIds?.forEach {
            val intent = Intent(context, StackWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, it)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            val remoteViews = RemoteViews(context?.packageName, R.layout.widget)
            remoteViews.setRemoteAdapter(R.id.stack_view, intent)
            remoteViews.setEmptyView(R.id.stack_view, R.id.empty_view)

            val action = Intent(context, FavoriteWidget::class.java)
            action.action = ACTION
            action.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, it)
            action.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

            val pendingIntent = getBroadcast(context, 0, action, FLAG_UPDATE_CURRENT)

            remoteViews.setPendingIntentTemplate(R.id.stack_view, pendingIntent)
            appWidgetManager?.updateAppWidget(it, remoteViews)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        intent?.let {
            if (it.action == ACTION)
                makeText(context, it.getStringExtra(EXTRA_ITEM), LENGTH_SHORT).show()

            if (it.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE || it.action == ACTION_UPDATE_WIDGET)
                updateWidget(context)
        }
    }

    private fun updateWidget(context: Context?) {
        val appWidgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(
            ComponentName(context?.packageName ?: return, FavoriteWidget::class.java.name)
        )
        AppWidgetManager.getInstance(context)
            .notifyAppWidgetViewDataChanged(appWidgetIds, R.id.imageView)
        AppWidgetManager.getInstance(context)
            .notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view)
        AppWidgetManager.getInstance(context)
            .notifyAppWidgetViewDataChanged(appWidgetIds, R.id.empty_view)
    }
}