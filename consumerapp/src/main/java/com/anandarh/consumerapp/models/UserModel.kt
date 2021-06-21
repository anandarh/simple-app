package com.anandarh.consumerapp.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    @SerializedName("avatar_url")
    val avatarUrl: String?,

    @SerializedName("company")
    val company: String?,

    @SerializedName("followers")
    val followers: Int,

    @SerializedName("following")
    val following: Int,

    @SerializedName("id")
    val id: Int,

    @SerializedName("location")
    val location: String?,

    @SerializedName("login")
    val login: String,

    @SerializedName("name")
    val name: String?,

    @SerializedName("public_repos")
    val publicRepos: Int,

    ) : Parcelable