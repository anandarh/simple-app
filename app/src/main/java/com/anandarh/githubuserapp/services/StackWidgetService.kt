package com.anandarh.githubuserapp.services

import android.content.Intent
import android.widget.RemoteViewsService
import com.anandarh.githubuserapp.factories.StackRemoteViewsFactory

class StackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory =
        StackRemoteViewsFactory(this.applicationContext)
}