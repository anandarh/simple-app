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

class FollowViewModel : ViewModel() {
    companion object {
        const val FOLLOWERS: String = "followers"
        const val FOLLOWING: String = "following"
    }


    private val _repository: UserRepository = UserRepository()
    private val _dataStateFollowers: MutableLiveData<DataState<ArrayList<UserModel>>> =
        MutableLiveData()
    private val _dataStateFollowing: MutableLiveData<DataState<ArrayList<UserModel>>> =
        MutableLiveData()


    val dataStateFollowers: LiveData<DataState<ArrayList<UserModel>>>
        get() = _dataStateFollowers
    val dataStateFollowing: LiveData<DataState<ArrayList<UserModel>>>
        get() = _dataStateFollowing


    fun getFollowersFollowing(username: String) {
        viewModelScope.launch {
            _repository.getFollow(username, FOLLOWERS).onEach { dataState ->
                _dataStateFollowers.postValue(dataState)
            }.launchIn(viewModelScope)

            _repository.getFollow(username, FOLLOWING).onEach { dataState ->
                _dataStateFollowing.postValue(dataState)
            }.launchIn(viewModelScope)
        }
    }
}