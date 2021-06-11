package com.anandarh.githubuserapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.anandarh.githubuserapp.R
import com.anandarh.githubuserapp.databinding.ItemUserFollowBinding
import com.anandarh.githubuserapp.models.UserListModel
import com.bumptech.glide.Glide

class FollowRecyclerViewAdapter(private val data: UserListModel) :
    RecyclerView.Adapter<FollowRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowRecyclerViewAdapter.ViewHolder {
        val binding =
            ItemUserFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            with(data.users[position]) {
                userAccount.text = login

                Glide.with(holder.itemView.context)
                    .load(avatarUrl)
                    .placeholder(
                        ContextCompat.getDrawable(
                            holder.itemView.context,
                            R.drawable.avatar_placeholder
                        )
                    )
                    .error(
                        ContextCompat.getDrawable(
                            holder.itemView.context,
                            R.drawable.error_image
                        )
                    )
                    .into(userImage)
            }
        }
    }

    override fun getItemCount() = data.users.size

    inner class ViewHolder(val binding: ItemUserFollowBinding) :
        RecyclerView.ViewHolder(binding.root)
}