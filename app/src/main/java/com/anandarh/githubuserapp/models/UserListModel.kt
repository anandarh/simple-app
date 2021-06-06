package com.anandarh.githubuserapp.models

import com.google.gson.annotations.SerializedName

data class UserListModel(
    @SerializedName("users")
    val users: List<UserModel>
)