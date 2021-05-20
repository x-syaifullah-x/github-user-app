package com.dicoding.picodiploma.githubuserapp.data.local.db

import android.net.Uri
import android.provider.BaseColumns
import com.dicoding.picodiploma.githubuserapp.data.local.entity.UserEntity

object DatabaseContract {

    const val AUTHORITY = "com.dicoding.picodiploma.githubuserapp"
    const val SCHEME = "content"

    class UserColumns : BaseColumns {

        companion object {
            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(UserEntity.TABLE_NAME)
                .build()
        }

    }
}