package com.anandarh.consumerapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anandarh.consumerapp.R
import com.anandarh.consumerapp.databinding.ItemUserBinding
import com.anandarh.consumerapp.models.UserListModel
import com.squareup.picasso.Picasso


class UsersRecyclerViewAdapter(users: UserListModel) :
    RecyclerView.Adapter<UsersRecyclerViewAdapter.UserViewHolder>() {

    private var data: UserListModel = users

    private lateinit var onItemClickListener: ItemClickListener

    fun setOnItemClickListener(onItemClickListener: ItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        with(data.items[position]) {

            holder.binding.apply {
                if (!name.isNullOrEmpty()) {
                    userName.text = name
                    userAccount.text = login
                    totalRepo.text = "$publicRepos"
                    repoIcon.visibility = View.VISIBLE
                    totalRepo.visibility = View.VISIBLE
                    titleRepo.visibility = View.VISIBLE
                } else {
                    userName.text = login
                    repoIcon.visibility = View.GONE
                    totalRepo.visibility = View.GONE
                    titleRepo.visibility = View.GONE
                }

                Picasso.get()
                    .load(avatarUrl)
                    .placeholder(R.drawable.avatar_placeholder)
                    .error(R.drawable.error_image)
                    .into(userImage)
            }

            holder.itemView.setOnClickListener {
                onItemClickListener.onItemClick(login)
            }
        }
    }

    override fun getItemCount() = data.items.size

    fun updateData(users: UserListModel) {
        data = users
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface ItemClickListener {
        fun onItemClick(username: String)
    }

}