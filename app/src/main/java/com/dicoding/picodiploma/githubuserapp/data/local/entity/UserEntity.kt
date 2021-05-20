package com.dicoding.picodiploma.githubuserapp.data.local.entity

import android.content.ContentValues
import android.database.Cursor
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = UserEntity.TABLE_NAME
)
data class UserEntity(

    @PrimaryKey
    @ColumnInfo(name = ID)
    val id: Long,

    @ColumnInfo(name = LOGIN)
    val login: String,

    @ColumnInfo(name = AVATAR_URL)
    val avatar_url: String
) {
    companion object {
        const val TABLE_NAME = "user"
        const val ID = "id"
        const val LOGIN = "login"
        const val AVATAR_URL = "avatar_url"

        const val COLUMN_INDEX_ID = 0
        const val COLUMN_INDEX_LOGIN = 1
        const val COLUMN_INDEX_AVATAR_URL = 2
    }
}

fun Cursor?.toUserEntity() = this?.run {
    if (moveToFirst()) {
        UserEntity(
            id = getLong(UserEntity.COLUMN_INDEX_ID),
            login = getString(UserEntity.COLUMN_INDEX_LOGIN),
            avatar_url = getString(UserEntity.COLUMN_INDEX_AVATAR_URL)
        )
    } else {
        null
    }
}

fun ContentValues?.toUserEntity() = this?.run {
    UserEntity(
        id = getAsLong(UserEntity.ID),
        login = getAsString(UserEntity.LOGIN),
        avatar_url = getAsString(UserEntity.AVATAR_URL)
    )
}