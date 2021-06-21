package com.anandarh.consumerapp.utilities

import android.database.Cursor
import com.anandarh.consumerapp.consumer.DatabaseContract
import com.anandarh.consumerapp.models.UserListModel
import com.anandarh.consumerapp.models.UserModel

object CursorMapper {
    fun mapCursorToUserListModel(notesCursor: Cursor?): UserListModel {
        val userList = mutableListOf<UserModel>()

        notesCursor?.apply {
            while (moveToNext()) {
                val login = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOGIN))
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.ID))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR))
                val company =
                    getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.COMPANY))
                val followers =
                    getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWERS))
                val following =
                    getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWING))
                val location =
                    getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOCATION))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.NAME))
                val repo = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.REPO))

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