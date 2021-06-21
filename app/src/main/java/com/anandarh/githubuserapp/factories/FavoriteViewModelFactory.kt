package com.anandarh.githubuserapp.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anandarh.githubuserapp.utilities.ResourceProvider
import com.anandarh.githubuserapp.viewmodels.FavoriteViewModel

@Suppress("UNCHECKED_CAST")
class FavoriteViewModelFactory(private val resourceProvider: ResourceProvider) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(resourceProvider) as T
        }
        throw IllegalArgumentException("unknown model class $modelClass")
    }
}