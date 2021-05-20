package com.dicoding.picodiploma.githubuserapp.provider

import android.appwidget.AppWidgetManager
import android.content.ContentProvider
import android.content.ContentValues
import android.content.Intent
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.dicoding.picodiploma.githubuserapp.data.local.dao.UserDao
import com.dicoding.picodiploma.githubuserapp.data.local.db.AppDatabase
import com.dicoding.picodiploma.githubuserapp.data.local.db.DatabaseContract.AUTHORITY
import com.dicoding.picodiploma.githubuserapp.data.local.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.dicoding.picodiploma.githubuserapp.data.local.entity.UserEntity.Companion.TABLE_NAME
import com.dicoding.picodiploma.githubuserapp.data.local.entity.toUserEntity
import com.dicoding.picodiploma.githubuserapp.widget.FavoriteWidget

class AppProvider : ContentProvider() {

    companion object {
        private const val USER = 1
        private const val USER_ID = 2
    }

    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    private lateinit var dao: UserDao

    init {
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, USER)
        sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", USER_ID)
    }

    override fun onCreate(): Boolean {
        context?.apply { dao = AppDatabase.getInstance(this).userDao() }
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            USER -> dao.getAll()
            USER_ID -> dao.getBy(uri.lastPathSegment?.toLongOrNull())
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added = when (USER) {
            sUriMatcher.match(uri) -> dao.insert(values.toUserEntity())
            else -> -1
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        if (added > 0) {
            updateWidget()
            return Uri.parse("$CONTENT_URI/$added")
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val res = when (sUriMatcher.match(uri)) {
            USER_ID -> {
                val id = uri.lastPathSegment?.toLongOrNull()
                val res = dao.delete(id)
                context?.contentResolver?.notifyChange(CONTENT_URI, null)
                res
            }
            else -> -1
        }

        if (res > 0) updateWidget()
        return res
    }

    override fun getType(uri: Uri): String? = null

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int = -1

    private fun updateWidget() {
        val intent = Intent(context, FavoriteWidget::class.java).apply {
            action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        }
        context?.sendBroadcast(intent)
    }
}