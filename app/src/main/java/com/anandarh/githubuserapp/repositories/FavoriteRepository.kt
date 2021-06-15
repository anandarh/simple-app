package com.anandarh.githubuserapp.repositories

import android.content.Context
import com.anandarh.githubuserapp.models.UserListModel
import com.anandarh.githubuserapp.models.UserModel
import com.anandarh.githubuserapp.room.LocalMapper
import com.anandarh.githubuserapp.room.UserDatabase
import com.anandarh.githubuserapp.utilities.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteRepository(context: Context) {

    private val userDatabase: UserDatabase = UserDatabase.invoke(context.applicationContext)
    private val localMapper: LocalMapper = LocalMapper()

    suspend fun addFavorite(user: UserModel): Flow<DataState<Long>> = flow {
        emit(DataState.Loading)
        delay(UserRepository.DURATION)
        try {
            val mapper = localMapper.mapToEntity(user)
            val result = userDatabase.userDao().insert(mapper)
            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getFavorites(): Flow<DataState<UserListModel>> = flow {
        emit(DataState.Loading)
        delay(UserRepository.DURATION)
        try {
            val users = userDatabase.userDao().get()
            val data = UserListModel(items = localMapper.mapFromEntityList(users))
            emit(DataState.Success(data))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}