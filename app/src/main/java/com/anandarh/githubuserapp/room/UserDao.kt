package com.anandarh.githubuserapp.room

import androidx.room.*
import com.anandarh.githubuserapp.constants.DatabaseConstant.Companion.TABLE_USERS_NAME

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserEntity): Long

    @Query("SELECT * FROM $TABLE_USERS_NAME")
    suspend fun get(): List<UserEntity>

    @Delete
    suspend fun delete(userEntity: UserEntity)
}
