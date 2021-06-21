package com.anandarh.githubuserapp.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.anandarh.githubuserapp.constants.DatabaseConstant.Companion.CONTENT_URI
import com.anandarh.githubuserapp.models.UserListModel
import com.anandarh.githubuserapp.models.UserModel
import com.anandarh.githubuserapp.room.LocalMapper
import com.anandarh.githubuserapp.room.UserDatabase
import com.anandarh.githubuserapp.utilities.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class FavoriteRepository(private val context: Context) {

    companion object {
        const val DURATION: Long = 500
    }

    private val userDatabase: UserDatabase = UserDatabase.invoke(context.applicationContext)
    private val localMapper: LocalMapper = LocalMapper()

    suspend fun addFavorite(user: UserModel) {
        try {
            val mapper = localMapper.mapToEntity(user)
            userDatabase.userDao().insert(mapper)
            context.contentResolver.insert(CONTENT_URI, null)
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    suspend fun getFavorites(): Flow<DataState<UserListModel>> = flow {
        emit(DataState.Loading)
        delay(DURATION)
        try {
            userDatabase.userDao().get().collect {
                val data = UserListModel(items = localMapper.mapFromEntityList(it))
                emit(DataState.Success(data))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun deleteFavorite(user: UserModel) {
        try {
            val mapper = localMapper.mapToEntity(user)
            userDatabase.userDao().delete(mapper)
            context.contentResolver.delete(CONTENT_URI, null, null)
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    fun isFavorited(username: String): LiveData<Boolean> = liveData {
        userDatabase.userDao().findByUsername(username).collect {
            emit(it != null)
        }
    }
}