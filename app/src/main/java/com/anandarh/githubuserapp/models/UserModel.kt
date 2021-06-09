package com.anandarh.githubuserapp.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("avatar_url")
    val avatar_url: String?,

    @SerializedName("bio")
    val bio: String?,

    @SerializedName("blog")
    val blog: String?,

    @SerializedName("company")
    val company: String?,

    @SerializedName("created_at")
    val created_at: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("events_url")
    val events_url: String?,

    @SerializedName("followers")
    val followers: Int,

    @SerializedName("followers_url")
    val followers_url: String?,

    @SerializedName("following")
    val following: Int,

    @SerializedName("following_url")
    val following_url: String?,

    @SerializedName("gists_url")
    val gists_url: String?,

    @SerializedName("gravatar_id")
    val gravatar_id: String?,

    @SerializedName("hireable")
    val hireable: String?,

    @SerializedName("html_url")
    val html_url: String?,

    @SerializedName("id")
    val id: Int,

    @SerializedName("location")
    val location: String?,

    @SerializedName("login")
    val login: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("node_id")
    val node_id: String?,

    @SerializedName("organizations_url")
    val organizations_url: String?,

    @SerializedName("public_gists")
    val public_gists: Int,

    @SerializedName("public_repos")
    val public_repos: Int,

    @SerializedName("received_events_url")
    val received_events_url: String?,

    @SerializedName("repos_url")
    val repos_url: String?,

    @SerializedName("site_admin")
    val site_admin: Boolean,

    @SerializedName("starred_url")
    val starred_url: String?,

    @SerializedName("subscriptions_url")
    val subscriptions_url: String?,

    @SerializedName("twitter_username")
    val twitter_username: String?,

    @SerializedName("type")
    val type: String?,

    @SerializedName("updated_at")
    val updated_at: String?,

    @SerializedName("url")
    val url: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readBoolean(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(avatar_url)
        parcel.writeString(bio)
        parcel.writeString(blog)
        parcel.writeString(company)
        parcel.writeString(created_at)
        parcel.writeString(email)
        parcel.writeString(events_url)
        parcel.writeInt(followers)
        parcel.writeString(followers_url)
        parcel.writeInt(following)
        parcel.writeString(following_url)
        parcel.writeString(gists_url)
        parcel.writeString(gravatar_id)
        parcel.writeString(hireable)
        parcel.writeString(html_url)
        parcel.writeInt(id)
        parcel.writeString(location)
        parcel.writeString(login)
        parcel.writeString(name)
        parcel.writeString(node_id)
        parcel.writeString(organizations_url)
        parcel.writeInt(public_gists)
        parcel.writeInt(public_repos)
        parcel.writeString(received_events_url)
        parcel.writeString(repos_url)
        parcel.writeBoolean(site_admin)
        parcel.writeString(starred_url)
        parcel.writeString(subscriptions_url)
        parcel.writeString(twitter_username)
        parcel.writeString(type)
        parcel.writeString(updated_at)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }
}