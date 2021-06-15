package com.anandarh.githubuserapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anandarh.githubuserapp.constants.DatabaseConstant.Companion.TABLE_USERS_NAME

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserEntity): Long

    @Query("SELECT * FROM $TABLE_USERS_NAME")
    suspend fun get(): List<UserEntity>
}
