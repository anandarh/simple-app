package com.anandarh.githubuserapp.constants

import android.net.Uri

class DatabaseConstant {
    companion object {
        const val DATABASE_NAME: String = "github_db"
        const val TABLE_USERS_NAME: String = "users"

        const val AUTHORITY = "com.anandarh.githubuserapp"

        const val LOGIN: String = "login"
        const val ID: String = "id"
        const val AVATAR: String = "avatar_url"
        const val COMPANY: String = "company"
        const val FOLLOWERS: String = "followers"
        const val FOLLOWING: String = "following"
        const val LOCATION: String = "location"
        const val NAME: String = "name"
        const val REPO: String = "public_repos"

        val CONTENT_URI: Uri = Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_USERS_NAME)
            .build()
    }
}