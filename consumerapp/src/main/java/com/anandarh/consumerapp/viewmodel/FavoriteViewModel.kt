package com.anandarh.consumerapp.viewmodel

import android.database.ContentObserver
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anandarh.consumerapp.consumer.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.anandarh.consumerapp.models.UserListModel
import com.anandarh.consumerapp.repository.FavoriteRepository
import com.anandarh.consumerapp.utilities.DataState
import com.anandarh.consumerapp.utilities.ResourceProvider
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FavoriteViewModel(private val resourceProvider: ResourceProvider) : ViewModel() {

    private lateinit var observer: ContentObserver

    private val _repository: FavoriteRepository =
        FavoriteRepository(resourceProvider.context.applicationContext)

    private val _dataState: MutableLiveData<DataState<UserListModel>> by lazy {
        MutableLiveData<DataState<UserListModel>>().also {
            observeUpdate()
        }
    }


    val dataState: LiveData<DataState<UserListModel>>
        get() = _dataState


    fun getFavorites() {
        viewModelScope.launch {
            _repository.getFavorites().onEach { dataState ->
                _dataState.postValue(dataState)
            }.launchIn(viewModelScope)
        }
    }

    private fun observeUpdate() {
        observer = object : ContentObserver(null) {
            override fun onChange(self: Boolean) {
                getFavorites()
            }
        }

        try {
            resourceProvider.context.contentResolver.registerContentObserver(
                CONTENT_URI,
                true,
                observer
            )
        } catch (e: Exception) {
            Toast.makeText(resourceProvider.context, e.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCleared() {
        super.onCleared()
        resourceProvider.context.contentResolver.unregisterContentObserver(observer)
    }

}