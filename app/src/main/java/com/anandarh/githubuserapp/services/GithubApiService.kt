package com.anandarh.githubuserapp.services

import com.anandarh.githubuserapp.BuildConfig
import com.anandarh.githubuserapp.models.GithubResponseModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") username: String
    ) : GithubResponseModel

    companion object {
        operator fun invoke(): GithubApiService {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubApiService::class.java)
        }
    }
}