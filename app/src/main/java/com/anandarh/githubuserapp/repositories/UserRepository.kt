package com.anandarh.githubuserapp.repositories

import android.content.Context
import com.anandarh.githubuserapp.constants.IntentConstant.Companion.JSON_ASSET_NAME
import com.anandarh.githubuserapp.models.UserListModel
import com.anandarh.githubuserapp.utilities.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class UserRepository(private val context: Context) {

    fun getUsers(): UserListModel {
        val gson = Gson()
        val jsonString = Utils().getJsonFromAssets(context, JSON_ASSET_NAME)

        return gson.fromJson(
            jsonString,
            UserListModel::class.java
        )
    }

}