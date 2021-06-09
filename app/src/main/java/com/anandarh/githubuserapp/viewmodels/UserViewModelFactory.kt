package com.anandarh.githubuserapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anandarh.githubuserapp.utilities.ResourceProvider

@Suppress("UNCHECKED_CAST")
class UserViewModelFactory constructor(private val resourceProvider: ResourceProvider) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(resourceProvider) as T
        }
        throw IllegalArgumentException("unknown model class $modelClass")
    }
}