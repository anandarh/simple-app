package com.anandarh.githubuserapp.repositories

import android.content.Context
import com.anandarh.githubuserapp.constants.IntentConstant.Companion.JSON_ASSET_NAME
import com.anandarh.githubuserapp.models.GithubResponseModel
import com.anandarh.githubuserapp.models.UserListModel
import com.anandarh.githubuserapp.services.GithubApiService
import com.anandarh.githubuserapp.utilities.DataState
import com.anandarh.githubuserapp.utilities.ResourceProvider
import com.anandarh.githubuserapp.utilities.Utils
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class UserRepository {

    private val apiService: GithubApiService = GithubApiService()

    suspend fun getLocalUsers(resourceProvider: ResourceProvider): Flow<DataState<GithubResponseModel>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val gson = Gson()
            val jsonString = Utils().getJsonFromAssets(resourceProvider.getContext(), JSON_ASSET_NAME)
            val localResponse = gson.fromJson(
                jsonString,
                UserListModel::class.java
            )
            val data = GithubResponseModel(false, listOf(), 0, localResponse)
            emit(DataState.Success(data))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getUsers(username: String): Flow<DataState<GithubResponseModel>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val githubResponse = apiService.searchUser(username)
            emit(DataState.Success(githubResponse))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }


}