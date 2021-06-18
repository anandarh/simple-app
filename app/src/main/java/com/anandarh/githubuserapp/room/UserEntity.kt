package com.anandarh.githubuserapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anandarh.githubuserapp.constants.DatabaseConstant.Companion.TABLE_USERS_NAME

@Entity(tableName = TABLE_USERS_NAME)
data class UserEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "login")
    val login: String,

    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String?,

    @ColumnInfo(name = "company")
    val company: String?,

    @ColumnInfo(name = "followers")
    val followers: Int,

    @ColumnInfo(name = "following")
    val following: Int,

    @ColumnInfo(name = "location")
    val location: String?,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "public_repos")
    val publicRepos: Int

)
