package com.dicoding.picodiploma.githubuserapp.widget

import android.content.Context
import android.content.Intent
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.githubuserapp.R
import com.dicoding.picodiploma.githubuserapp.data.local.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.dicoding.picodiploma.githubuserapp.data.remote.response.User
import com.dicoding.picodiploma.githubuserapp.data.remote.response.toListUser
import com.dicoding.picodiploma.githubuserapp.widget.FavoriteWidget.Companion.EXTRA_ITEM

class StackWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?) = StackRemoteViewsFactory(this)

    inner class StackRemoteViewsFactory(private val context: Context) : RemoteViewsFactory {

        private val data: ArrayList<User> = arrayListOf()

        override fun onCreate() {}

        override fun onDataSetChanged() {
            val clearCallingIdentity= Binder.clearCallingIdentity()
            val cursor = context.contentResolver.query(CONTENT_URI, null, null, null, null)
            data.clear()
            data.addAll(cursor.toListUser())
            cursor?.close()
            Binder.restoreCallingIdentity(clearCallingIdentity)
        }

        override fun onDestroy() {
            data.clear()
        }

        override fun getCount() = data.size

        override fun getViewAt(position: Int): RemoteViews {
            val remoteViews = RemoteViews(context.packageName, R.layout.widget_item)

            try {
                if (data.isNotEmpty()) {
                    val bitmap = Glide.with(context)
                        .asBitmap()
                        .load(data[position].avatar_url)
                        .submit()
                        .get()
                    remoteViews.setImageViewBitmap(R.id.imageView, bitmap)
                    remoteViews.setTextViewText(R.id.widget_title, data[position].login)
                    remoteViews.setOnClickFillInIntent(
                        R.id.imageView, Intent().putExtra(EXTRA_ITEM, data[position].login)
                    )
                    Binder.restoreCallingIdentity(Binder.clearCallingIdentity())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return remoteViews
        }

        override fun getLoadingView() = RemoteViews(context.packageName, R.layout.widget_item)

        override fun getViewTypeCount() = 1

        override fun getItemId(position: Int) = position.toLong()

        override fun hasStableIds() = false
    }
}