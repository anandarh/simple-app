package com.anandarh.githubuserapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anandarh.githubuserapp.R
import com.anandarh.githubuserapp.databinding.ItemUserFollowBinding
import com.anandarh.githubuserapp.models.UserModel
import com.squareup.picasso.Picasso

class FollowRecyclerViewAdapter(private val data: ArrayList<UserModel>) :
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
            with(data[position]) {
                userAccount.text = login

                Picasso.get()
                    .load(avatarUrl)
                    .placeholder(R.drawable.avatar_placeholder)
                    .error(R.drawable.error_image)
                    .into(userImage)
            }
        }
    }

    override fun getItemCount() = data.size

    inner class ViewHolder(val binding: ItemUserFollowBinding) :
        RecyclerView.ViewHolder(binding.root)
}