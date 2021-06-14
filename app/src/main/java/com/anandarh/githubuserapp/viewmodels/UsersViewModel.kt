package com.anandarh.githubuserapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anandarh.githubuserapp.models.UserListModel
import com.anandarh.githubuserapp.repositories.UserRepository
import com.anandarh.githubuserapp.utilities.DataState
import com.anandarh.githubuserapp.utilities.ResourceProvider
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class UsersViewModel(private val resourceProvider: ResourceProvider) : ViewModel() {
    private val _userRepository: UserRepository = UserRepository()
    private val _dataState: MutableLiveData<DataState<UserListModel>> by lazy {
        MutableLiveData<DataState<UserListModel>>().also {
            setStateEvent(UserStateEvent.GetUsersEvent)
        }
    }
    private val _searchQuery: MutableLiveData<String> = MutableLiveData()

    val dataState: LiveData<DataState<UserListModel>>
        get() = _dataState

    val searchQuery: LiveData<String>
        get() = _searchQuery

    fun setSearchQuery(query: String) {
        _searchQuery.postValue(query)
    }

    fun setStateEvent(userStateEvent: UserStateEvent) {

        viewModelScope.launch {
            when (userStateEvent) {
                is UserStateEvent.GetSearchUserEvent -> {
                    _userRepository.getUsers(userStateEvent.username).onEach { dataState ->
                        _dataState.postValue(dataState)
                    }
                        .launchIn(viewModelScope)
                }
                UserStateEvent.GetUsersEvent -> {
                    _userRepository.getLocalUsers(resourceProvider).onEach { dataState ->
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