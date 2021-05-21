package com.dicoding.picodiploma.githubuserapp.provider

import android.net.Uri
import android.provider.BaseColumns
import com.dicoding.picodiploma.githubuserapp.data.local.entity.UserEntity

object AppProviderContract {

    const val AUTHORITY = "com.dicoding.picodiploma.githubuserapp"
    const val SCHEME = "content"

    class UserColumns : BaseColumns {

        companion object {
            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(UserEntity.TABLE_NAME)
                .build()

            val CONTENT_URI_ID = { id: Any? -> Uri.parse("$CONTENT_URI/${id ?: -1}") }
        }

    }
}