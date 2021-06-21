package com.anandarh.consumerapp.repository

import android.content.Context
import com.anandarh.consumerapp.consumer.DatabaseContract
import com.anandarh.consumerapp.models.UserListModel
import com.anandarh.consumerapp.utilities.CursorMapper
import com.anandarh.consumerapp.utilities.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteRepository(private val context: Context) {

    companion object {
        const val DURATION: Long = 500
    }

    suspend fun getFavorites(): Flow<DataState<UserListModel>> = flow {
        emit(DataState.Loading)
        delay(DURATION)
        try {
            val cursor = context.contentResolver?.query(
                DatabaseContract.UserColumns.CONTENT_URI,
                null,
                null,
                null,
                null
            )
            val users = CursorMapper.mapCursorToUserListModel(cursor)
            emit(DataState.Success(users))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}