package com.anandarh.githubuserapp.utilities

import android.database.Cursor
import com.anandarh.githubuserapp.constants.DatabaseConstant
import com.anandarh.githubuserapp.models.UserListModel
import com.anandarh.githubuserapp.models.UserModel


object CursorMapper {
    fun mapCursorToUserListModel(notesCursor: Cursor?): UserListModel {
        val userList = mutableListOf<UserModel>()

        notesCursor?.apply {
            while (moveToNext()) {
                val login = getString(getColumnIndexOrThrow(DatabaseConstant.LOGIN))
                val id = getInt(getColumnIndexOrThrow(DatabaseConstant.ID))
                val avatar = getString(getColumnIndexOrThrow(DatabaseConstant.AVATAR))
                val company =
                    getString(getColumnIndexOrThrow(DatabaseConstant.COMPANY))
                val followers =
                    getInt(getColumnIndexOrThrow(DatabaseConstant.FOLLOWERS))
                val following =
                    getInt(getColumnIndexOrThrow(DatabaseConstant.FOLLOWING))
                val location =
                    getString(getColumnIndexOrThrow(DatabaseConstant.LOCATION))
                val name = getString(getColumnIndexOrThrow(DatabaseConstant.NAME))
                val repo = getInt(getColumnIndexOrThrow(DatabaseConstant.REPO))

                userList.add(
                    UserModel(
                        avatar,
                        company,
                        followers,
                        following,
                        id,
                        location,
                        login,
                        name,
                        repo
                    )
                )
            }
        }
        return UserListModel(userList)
    }
}