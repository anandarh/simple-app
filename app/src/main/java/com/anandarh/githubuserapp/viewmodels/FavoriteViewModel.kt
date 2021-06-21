package com.anandarh.githubuserapp.viewmodels

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anandarh.githubuserapp.GitFavoriteWidget
import com.anandarh.githubuserapp.R
import com.anandarh.githubuserapp.models.UserListModel
import com.anandarh.githubuserapp.models.UserModel
import com.anandarh.githubuserapp.repositories.FavoriteRepository
import com.anandarh.githubuserapp.utilities.DataState
import com.anandarh.githubuserapp.utilities.ResourceProvider
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FavoriteViewModel(private val resourceProvider: ResourceProvider) : ViewModel() {
    private val _repository: FavoriteRepository =
        FavoriteRepository(resourceProvider.context.applicationContext)
    private val _dataState: MutableLiveData<DataState<UserListModel>> = MutableLiveData()

    val dataState: LiveData<DataState<UserListModel>>
        get() = _dataState

    fun isFavorited(username: String): LiveData<Boolean> =
        _repository.isFavorited(username)

    fun getFavorites() {
        viewModelScope.launch {
            _repository.getFavorites().onEach { dataState ->
                _dataState.value = dataState
            }.launchIn(viewModelScope)
        }
    }

    fun addFavorite(user: UserModel) {
        viewModelScope.launch {
            _repository.addFavorite(user)
            refreshWidget()
        }
    }

    fun deleteFavorite(user: UserModel) {
        viewModelScope.launch {
            _repository.deleteFavorite(user)
            refreshWidget()
        }
    }

    private fun refreshWidget() {
        val appWidgetManager =
            AppWidgetManager.getInstance(resourceProvider.context.applicationContext)
        val thisAppWidget = ComponentName(
            resourceProvider.context.applicationContext.packageName,
            GitFavoriteWidget::class.java.name
        )
        val appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget)
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stackView)
    }

}