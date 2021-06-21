package com.anandarh.consumerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anandarh.consumerapp.utilities.ResourceProvider


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