package com.anandarh.githubuserapp.factories

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.anandarh.githubuserapp.GitFavoriteWidget
import com.anandarh.githubuserapp.R
import com.anandarh.githubuserapp.models.UserListModel
import com.anandarh.githubuserapp.room.UserDatabase
import com.anandarh.githubuserapp.utilities.CircleTransform
import com.anandarh.githubuserapp.utilities.CursorMapper
import com.squareup.picasso.Picasso

internal class StackRemoteViewsFactory(private val context: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private var users: UserListModel = UserListModel(listOf())

    override fun onCreate() {

    }

    override fun onDataSetChanged() {
        val userDatabase = UserDatabase.invoke(context.applicationContext)
        val data = userDatabase.providerDao.getAll()
        users = CursorMapper.mapCursorToUserListModel(data)
    }

    override fun onDestroy() {

    }

    override fun getCount(): Int = users.items.size

    override fun getViewAt(position: Int): RemoteViews {
        val user = users.items[position]

        val rv = RemoteViews(context.packageName, R.layout.item_widget)

        val avatar: Bitmap = Picasso.get()
            .load(user.avatarUrl)
            .transform(CircleTransform())
            .placeholder(R.drawable.avatar_placeholder)
            .error(R.drawable.error_image)
            .get()

        rv.setImageViewBitmap(R.id.userImage, avatar)
        rv.setTextViewText(R.id.userName, user.name)
        rv.setTextViewText(R.id.userAccount, user.login)

        val extras = bundleOf(
            GitFavoriteWidget.EXTRA_ITEM to user.name
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.mainViewWidget, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}