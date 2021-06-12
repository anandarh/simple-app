package com.anandarh.githubuserapp.services

import com.anandarh.githubuserapp.BuildConfig
import com.anandarh.githubuserapp.models.GithubItemModel
import com.anandarh.githubuserapp.models.GithubResponseModel
import com.anandarh.githubuserapp.models.UserModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {

    // @Headers("Authorization: $GITHUB_TOKEN")
    @GET("search/users")
    suspend fun searchUser(
        @Query("q") username: String
    ): GithubResponseModel

    // @Headers("Authorization: $GITHUB_TOKEN")
    @GET("users/{username}")
    suspend fun detailUser(
        @Path("username") username: String
    ): UserModel

    // @Headers("Authorization: $GITHUB_TOKEN")
    @GET("users/{username}/{followType}")
    suspend fun getFollow(
        @Path("username") username: String,
        @Path("followType") followType: String
    ): ArrayList<GithubItemModel>

    companion object {
        operator fun invoke(): GithubApiService {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubApiService::class.java)
        }

        // const val GITHUB_TOKEN:String = "xxx_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
    }
}