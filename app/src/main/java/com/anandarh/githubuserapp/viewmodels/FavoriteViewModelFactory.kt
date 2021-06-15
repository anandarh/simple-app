package com.anandarh.githubuserapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anandarh.githubuserapp.utilities.ResourceProvider

class FavoriteViewModelFactory(private val resourceProvider: ResourceProvider) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(resourceProvider) as T
        }
        throw IllegalArgumentException("unknown model class $modelClass")
    }
}