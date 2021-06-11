package com.anandarh.githubuserapp.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("avatar_url")
    val avatarUrl: String?,

    @SerializedName("bio")
    val bio: String?,

    @SerializedName("blog")
    val blog: String?,

    @SerializedName("company")
    val company: String?,

    @SerializedName("created_at")
    val createdAt: String?,

    @SerializedName("email")
    val email: String?,

    @SerializedName("events_url")
    val eventsUrl: String?,

    @SerializedName("followers")
    val followers: Int,

    @SerializedName("followers_url")
    val followersUrl: String?,

    @SerializedName("following")
    val following: Int,

    @SerializedName("following_url")
    val followingUrl: String?,

    @SerializedName("gists_url")
    val gistsUrl: String?,

    @SerializedName("gravatar_id")
    val gravatarId: String?,

    @SerializedName("hireable")
    val hireable: String?,

    @SerializedName("html_url")
    val htmlUrl: String?,

    @SerializedName("id")
    val id: Int,

    @SerializedName("location")
    val location: String?,

    @SerializedName("login")
    val login: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("node_id")
    val nodeId: String?,

    @SerializedName("organizations_url")
    val organizationsUrl: String?,

    @SerializedName("public_gists")
    val publicGists: Int,

    @SerializedName("public_repos")
    val publicRepos: Int,

    @SerializedName("received_events_url")
    val receivedEventsUrl: String?,

    @SerializedName("repos_url")
    val reposUrl: String?,

    @SerializedName("site_admin")
    val siteAdmin: Boolean,

    @SerializedName("starred_url")
    val starredUrl: String?,

    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String?,

    @SerializedName("twitter_username")
    val twitterUsername: String?,

    @SerializedName("type")
    val type: String?,

    @SerializedName("updated_at")
    val updatedAt: String?,

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
        parcel.writeString(avatarUrl)
        parcel.writeString(bio)
        parcel.writeString(blog)
        parcel.writeString(company)
        parcel.writeString(createdAt)
        parcel.writeString(email)
        parcel.writeString(eventsUrl)
        parcel.writeInt(followers)
        parcel.writeString(followersUrl)
        parcel.writeInt(following)
        parcel.writeString(followingUrl)
        parcel.writeString(gistsUrl)
        parcel.writeString(gravatarId)
        parcel.writeString(hireable)
        parcel.writeString(htmlUrl)
        parcel.writeInt(id)
        parcel.writeString(location)
        parcel.writeString(login)
        parcel.writeString(name)
        parcel.writeString(nodeId)
        parcel.writeString(organizationsUrl)
        parcel.writeInt(publicGists)
        parcel.writeInt(publicRepos)
        parcel.writeString(receivedEventsUrl)
        parcel.writeString(reposUrl)
        parcel.writeBoolean(siteAdmin)
        parcel.writeString(starredUrl)
        parcel.writeString(subscriptionsUrl)
        parcel.writeString(twitterUsername)
        parcel.writeString(type)
        parcel.writeString(updatedAt)
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