package com.anandarh.githubuserapp.constants

import android.net.Uri

class DatabaseConstant {
    companion object {
        const val DATABASE_NAME: String = "github_db"
        const val TABLE_USERS_NAME: String = "users"

        const val AUTHORITY = "com.anandarh.githubuserapp"

        val CONTENT_URI: Uri = Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_USERS_NAME)
            .build()
    }
}