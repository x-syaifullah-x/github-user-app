package com.dicoding.picodiploma.githubuserapp.data.local.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.picodiploma.githubuserapp.data.local.entity.UserEntity
import com.dicoding.picodiploma.githubuserapp.data.local.entity.UserEntity.Companion.TABLE_NAME

@Dao
interface UserDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll(): Cursor

    @Query("SELECT * FROM $TABLE_NAME WHERE id=:id")
    fun getBy(id: Long?): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(values: UserEntity?): Long

    @Query("DELETE FROM $TABLE_NAME WHERE id=:id")
    fun delete(id: Long?): Int
}