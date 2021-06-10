package com.anandarh.githubuserapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.anandarh.githubuserapp.R
import com.anandarh.githubuserapp.constants.IntentConstant.Companion.EXTRA_USERNAME
import com.anandarh.githubuserapp.databinding.ActivityUserDetailBinding
import com.anandarh.githubuserapp.models.UserModel
import com.anandarh.githubuserapp.utilities.DataState
import com.anandarh.githubuserapp.viewmodels.UserDetailViewModel
import com.bumptech.glide.Glide


class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var viewModel: UserDetailViewModel

    private lateinit var data: UserModel
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(EXTRA_USERNAME).orEmpty()

        initializeViewModel()
        backOnClickAction()
    }

    private fun backOnClickAction() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(this).get(UserDetailViewModel::class.java)

        viewModel.dataState.observe(this, { dataState ->
            when (dataState) {
                is DataState.Success<UserModel> -> {
                    displayProgressBar(false)
                    data = dataState.data
                    displayData()
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.toString())
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })

        viewModel.getUserDetail(username)
    }

    @SuppressLint("SetTextI18n")
    private fun displayData() {
        Glide.with(this@UserDetailActivity)
            .load(data.avatar_url)
            .placeholder(
                ContextCompat.getDrawable(
                    this@UserDetailActivity,
                    R.drawable.avatar_placeholder
                )
            )
            .error(
                ContextCompat.getDrawable(
                    this@UserDetailActivity,
                    R.drawable.error_image
                )
            )
            .into(binding.userImage)

        binding.apply {
            userName.text = data.name
            userCompany.text = data.company
            userLocation.text = data.location
            totalRepo.text = data.public_repos.toString()
            totalFollowers.text = data.followers.toString()
            totalFollowing.text = data.following.toString()
        }

        dataOnClickAction()
    }

    private fun displayError(error: String) {
        binding.errorContainer.apply {
            root.visibility = View.VISIBLE
            errorDesc.text = error
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        binding.loadingContainer.root.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun dataOnClickAction() {
        binding.btnGithub.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                val browserIntent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse("https://github.com/${this@UserDetailActivity.data.login}")
                }
                startActivity(browserIntent)
            }
        }
    }
}