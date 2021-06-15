package com.anandarh.githubuserapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anandarh.githubuserapp.models.UserListModel
import com.anandarh.githubuserapp.models.UserModel
import com.anandarh.githubuserapp.repositories.FavoriteRepository
import com.anandarh.githubuserapp.utilities.DataState
import com.anandarh.githubuserapp.utilities.ResourceProvider
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FavoriteViewModel(resourceProvider: ResourceProvider) : ViewModel() {
    private val _repository: FavoriteRepository =
        FavoriteRepository(resourceProvider.context.applicationContext)
    private val _dataState: MutableLiveData<DataState<UserListModel>> = MutableLiveData()

    val dataState: LiveData<DataState<UserListModel>>
        get() = _dataState

    fun getFavorites() {
        viewModelScope.launch {
            _repository.getFavorites().onEach { dataState ->
                _dataState.postValue(dataState)
            }.launchIn(viewModelScope)
        }
    }

    fun addFavorite(user: UserModel) {
        viewModelScope.launch {
            _repository.addFavorite(user).launchIn(viewModelScope)
        }
    }

}