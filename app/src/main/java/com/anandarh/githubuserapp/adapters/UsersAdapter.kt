package com.anandarh.githubuserapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anandarh.githubuserapp.R
import com.anandarh.githubuserapp.models.GithubResponseModel
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView


class UsersAdapter(private val data: GithubResponseModel) :
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private lateinit var onItemClickListener: ItemClickListener

    private val searchLayout:Int = 0
    private val defaultLayout:Int = 1

    fun setOnItemClickListener(onItemClickListener: ItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun getItemViewType(position: Int): Int {
        return if (data.userListModel != null) defaultLayout else searchLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = if (viewType == searchLayout) {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user_search, parent, false)
        } else {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user, parent, false)
        }
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        if (data.userListModel != null) {
            with(data.userListModel.users[position]) {
                holder.userName.text = name
                holder.userAccount.text = login
                holder.totalRepo.text = "$public_repos"

                val imageResource: Int = holder.itemView.context.resources.getIdentifier(
                    avatar_url,
                    null,
                    holder.itemView.context.packageName
                )

                Glide.with(holder.itemView.context)
                    .load(imageResource)
                    .into(holder.userImage)

                holder.itemView.setOnClickListener {
                    onItemClickListener.onItemClick(login.orEmpty())
                }
            }
        } else {
            with(data.items[position]) {
                holder.userAccount.text = login

                Glide.with(holder.itemView.context)
                    .load(avatarUrl)
                    .into(holder.userImage)

                holder.itemView.setOnClickListener {
                    onItemClickListener.onItemClick(login)
                }
            }
        }
    }

    override fun getItemCount() =
        if (data.userListModel != null) data.userListModel.users.size else data.items.size

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userImage: CircleImageView = itemView.findViewById(R.id.userImage)
        var userAccount: TextView = itemView.findViewById(R.id.userAccount)
        var userName: TextView = itemView.findViewById(R.id.userName)
        var totalRepo: TextView = itemView.findViewById(R.id.totalRepo)
    }

    interface ItemClickListener {
        fun onItemClick(username: String)
    }

}