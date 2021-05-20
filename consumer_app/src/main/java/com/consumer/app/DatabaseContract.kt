package com.consumer.app

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    private const val AUTHORITY = "com.dicoding.picodiploma.githubuserapp"
    private const val SCHEME = "content"
    private const val TABLE_NAME = "user"

    class UserColumns : BaseColumns {

        companion object {
            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }

    }
}