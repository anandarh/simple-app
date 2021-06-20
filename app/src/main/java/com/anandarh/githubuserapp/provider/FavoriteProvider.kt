package com.anandarh.githubuserapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.anandarh.githubuserapp.constants.DatabaseConstant.Companion.AUTHORITY
import com.anandarh.githubuserapp.constants.DatabaseConstant.Companion.TABLE_USERS_NAME
import com.anandarh.githubuserapp.room.UserDatabase

class FavoriteProvider : ContentProvider() {

    companion object {
        private const val FAV = 1
        private const val FAV_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var userDatabase: UserDatabase

        init {
            // content://com.anandarh.githubuserapp/users
            sUriMatcher.addURI(AUTHORITY, TABLE_USERS_NAME, FAV)

            // content://com.anandarh.githubuserapp/users/id
            sUriMatcher.addURI(AUTHORITY, "$TABLE_USERS_NAME/#", FAV_ID)
        }

    }

    override fun onCreate(): Boolean {
        userDatabase = UserDatabase.invoke(context as Context)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            FAV -> userDatabase.providerDao.getAll()
            FAV_ID -> userDatabase.providerDao.findByUsername(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }


    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }
}