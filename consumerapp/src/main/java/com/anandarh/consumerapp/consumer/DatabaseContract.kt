package com.anandarh.consumerapp.consumer

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.anandarh.githubuserapp"
    const val SCHEME = "content"

    class UserColumns : BaseColumns {

        companion object {
            private const val TABLE_NAME: String = "users"

            const val LOGIN: String = "login"
            const val ID: String = "id"
            const val AVATAR: String = "avatar_url"
            const val COMPANY: String = "company"
            const val FOLLOWERS: String = "followers"
            const val FOLLOWING: String = "following"
            const val LOCATION: String = "location"
            const val NAME: String = "name"
            const val REPO: String = "public_repos"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }

    }
}