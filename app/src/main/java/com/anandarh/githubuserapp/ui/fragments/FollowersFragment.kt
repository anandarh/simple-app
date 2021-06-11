package com.anandarh.githubuserapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.anandarh.githubuserapp.R
import com.anandarh.githubuserapp.adapters.FollowRecyclerViewAdapter
import com.anandarh.githubuserapp.databinding.FragmentFollowersBinding
import com.anandarh.githubuserapp.models.UserListModel
import com.anandarh.githubuserapp.models.UserModel
import com.anandarh.githubuserapp.viewmodels.FollowViewModel

class FollowersFragment : Fragment(R.layout.fragment_followers) {

    private lateinit var binding: FragmentFollowersBinding
    private lateinit var mAdapter: FollowRecyclerViewAdapter

    private val viewModel: FollowViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFollowersBinding.bind(view)

        val data = UserModel(
            "https://avatars.githubusercontent.com/u/1024025?v=4",
            null,
            null,
            null,
            null,
            null,
            null,
            0,
            null,
            0,
            null,
            null,
            null,
            null,
            null,
            0,
            null,
            "torvalds",
            null, null, null, 0, 0,
            null,
            null,
            false,
            null,
            null,
            null,
            null, null, null
        )

        val listData: List<UserModel> = listOf(data)

        mAdapter = FollowRecyclerViewAdapter(UserListModel(listData))
        binding.rvUser.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@FollowersFragment.context)
        }
    }

}