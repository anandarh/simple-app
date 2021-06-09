package com.anandarh.githubuserapp.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anandarh.githubuserapp.models.GithubResponseModel
import com.anandarh.githubuserapp.repositories.UserRepository
import com.anandarh.githubuserapp.utilities.DataState
import com.anandarh.githubuserapp.utilities.ResourceProvider
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class UserViewModel(private val resourceProvider: ResourceProvider) : ViewModel() {
    private val _userRepository:UserRepository = UserRepository()
    private val _dataState: MutableLiveData<DataState<GithubResponseModel>> by lazy {
        MutableLiveData<DataState<GithubResponseModel>>().also {
            setStateEvent(UserStateEvent.GetUsersEvent)
        }
    }

    val dataState: LiveData<DataState<GithubResponseModel>>
        get() = _dataState

    fun setStateEvent(userStateEvent: UserStateEvent){

        viewModelScope.launch {
            when (userStateEvent) {
                is UserStateEvent.GetSearchUserEvent -> {
                    _userRepository.getUsers(userStateEvent.username).onEach {
                        dataState ->
                        _dataState.postValue(dataState)
                    }
                        .launchIn(viewModelScope)
                }
                UserStateEvent.GetUsersEvent -> {
                    _userRepository.getLocalUsers(resourceProvider).onEach {
                            dataState ->
                        _dataState.postValue(dataState)
                    }
                        .launchIn(viewModelScope)
                }
            }
        }
    }
}

sealed class UserStateEvent {
    object GetUsersEvent : UserStateEvent()
    data class GetSearchUserEvent(val username: String) : UserStateEvent()
}