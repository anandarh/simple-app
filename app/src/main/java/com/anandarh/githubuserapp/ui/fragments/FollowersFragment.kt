package com.anandarh.githubuserapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.anandarh.githubuserapp.R
import com.anandarh.githubuserapp.adapters.FollowRecyclerViewAdapter
import com.anandarh.githubuserapp.databinding.FragmentFollowersFollowingBinding
import com.anandarh.githubuserapp.models.UserModel
import com.anandarh.githubuserapp.utilities.DataState
import com.anandarh.githubuserapp.viewmodels.FollowViewModel

class FollowersFragment : Fragment(R.layout.fragment_followers_following) {

    private lateinit var binding: FragmentFollowersFollowingBinding

    private val viewModel: FollowViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFollowersFollowingBinding.bind(view)

        viewModel.dataStateFollowers.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success<ArrayList<UserModel>> -> {
                    displayProgressBar(false)
                    displayData(dataState.data)
                }

                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.toString())
                }

                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })

    }

    private fun displayData(data: ArrayList<UserModel>) {
        binding.rvUser.apply {
            adapter = FollowRecyclerViewAdapter(data)
            layoutManager = LinearLayoutManager(this@FollowersFragment.context)
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        binding.progressBar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayError(error: String) {
        binding.errorContainer.apply {
            root.visibility = View.VISIBLE
            errorDesc.text = error
        }
    }


}