package com.anandarh.githubuserapp.room

import androidx.room.*
import com.anandarh.githubuserapp.constants.DatabaseConstant.Companion.TABLE_USERS_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserEntity)

    @Query("SELECT * FROM $TABLE_USERS_NAME")
    fun get(): Flow<List<UserEntity>>

    @Delete
    suspend fun delete(userEntity: UserEntity)

    @Query("SELECT * FROM $TABLE_USERS_NAME WHERE login = :username LIMIT 1")
    fun findByUsername(username: String): Flow<UserEntity?>
}
