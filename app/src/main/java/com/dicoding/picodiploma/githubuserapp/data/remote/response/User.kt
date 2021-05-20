package com.dicoding.picodiploma.githubuserapp.data.remote.response

import android.content.ContentValues
import android.database.Cursor
import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @ColumnInfo(name = FIELD_ID)
    val id: Int,

    @ColumnInfo(name = FIELD_LOGIN)
    val login: String,

    @ColumnInfo(name = FIELD_AVATAR_URL)
    val avatar_url: String
) : Parcelable {
    companion object {
        const val COLUMN_INDEX_ID = 0
        const val COLUMN_INDEX_LOGIN = 1
        const val COLUMN_INDEX_AVATAR_URL = 2

        const val FIELD_ID = "id"
        const val FIELD_LOGIN = "login"
        const val FIELD_AVATAR_URL = "avatar_url"
    }
}

fun User.toContentValues() = ContentValues().apply {
    put(User.FIELD_ID, id)
    put(User.FIELD_LOGIN, login)
    put(User.FIELD_AVATAR_URL, avatar_url)
}

fun Cursor?.toListUser() = arrayListOf<User>().apply {
    while (this@toListUser != null && moveToNext()) {
        val user = User(
            id = getInt(User.COLUMN_INDEX_ID),
            login = getString(User.COLUMN_INDEX_LOGIN),
            avatar_url = getString(User.COLUMN_INDEX_AVATAR_URL)
        ); add(user)
    }
}