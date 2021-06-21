package com.anandarh.githubuserapp.room

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Query
import com.anandarh.githubuserapp.constants.DatabaseConstant

@Dao
interface ProviderDao {

    @Query("SELECT * FROM ${DatabaseConstant.TABLE_USERS_NAME}")
    fun getAll(): Cursor

    @Query("SELECT * FROM ${DatabaseConstant.TABLE_USERS_NAME} WHERE login = :username LIMIT 1")
    fun findByUsername(username: String): Cursor

}