package com.anandarh.githubuserapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anandarh.githubuserapp.models.UserModel
import com.anandarh.githubuserapp.repositories.UserRepository
import com.anandarh.githubuserapp.utilities.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class UserDetailViewModel : ViewModel() {
    private val _repository: UserRepository = UserRepository()
    private val _dataState: MutableLiveData<DataState<UserModel>> = MutableLiveData()

    val dataState: LiveData<DataState<UserModel>>
        get() = _dataState

    fun getUserDetail(username: String) {
        viewModelScope.launch {
            _repository.getDetailUser(username).onEach { dataState ->
                _dataState.postValue(dataState)
            }
                .launchIn(viewModelScope)
        }
    }
}