package com.consumer.app

data class Model(
    val id: Int,
    val login: String,
    val avatar_url: String
) {
    companion object {
        const val COLUMN_INDEX_ID = 0
        const val COLUMN_INDEX_LOGIN = 1
        const val COLUMN_INDEX_AVATAR_URL = 2
    }
}