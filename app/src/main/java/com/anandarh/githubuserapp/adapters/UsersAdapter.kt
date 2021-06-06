package com.anandarh.githubuserapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anandarh.githubuserapp.databinding.ItemUserBinding
import com.anandarh.githubuserapp.models.UserListModel
import com.anandarh.githubuserapp.models.UserModel
import com.bumptech.glide.Glide

class UsersAdapter(private val list: UserListModel) :
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private lateinit var onItemClickListener: ItemClickListener

    fun setOnItemClickListener(onItemClickListener: ItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        with(holder) {
            with(list.users[position]) {
                binding.userName.text = name
                binding.userAccount.text = username
                binding.totalRepo.text = "$repository"

                val imageResource: Int = holder.itemView.context.resources.getIdentifier(
                    avatar,
                    null,
                    holder.itemView.context.packageName
                )

                Glide.with(holder.itemView.context)
                    .load(imageResource)
                    .into(binding.userImage)

                holder.itemView.setOnClickListener {
                    onItemClickListener.onItemClick(list.users[position])
                }
            }
        }
    }

    override fun getItemCount() = list.users.size

    inner class UserViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    interface ItemClickListener {
        fun onItemClick(data: UserModel)
    }

}