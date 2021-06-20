package com.anandarh.githubuserapp.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.anandarh.githubuserapp.BuildConfig
import com.anandarh.githubuserapp.R
import com.anandarh.githubuserapp.adapters.FollowPagerAdapter
import com.anandarh.githubuserapp.constants.IntentConstant.Companion.EXTRA_USERNAME
import com.anandarh.githubuserapp.databinding.ActivityUserDetailBinding
import com.anandarh.githubuserapp.models.UserModel
import com.anandarh.githubuserapp.utilities.DataState
import com.anandarh.githubuserapp.utilities.ResourceProvider
import com.anandarh.githubuserapp.viewmodels.FavoriteViewModel
import com.anandarh.githubuserapp.viewmodels.FavoriteViewModelFactory
import com.anandarh.githubuserapp.viewmodels.FollowViewModel
import com.anandarh.githubuserapp.viewmodels.UserDetailViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso


class UserDetailActivity : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    private val viewModel: UserDetailViewModel by viewModels()
    private val viewModelFollow: FollowViewModel by viewModels()
    private val viewModelFavorite: FavoriteViewModel by viewModels(
        factoryProducer = { FavoriteViewModelFactory(ResourceProvider(this)) }
    )

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var data: UserModel
    private lateinit var username: String

    private var isFavorited = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(EXTRA_USERNAME).orEmpty()

        initializeUI()
        initializeViewModel()
        initializeTabView()

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        viewModel.dataState.observe(this, { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    menuInflater.inflate(R.menu.detail_menu, menu)
                }
                else -> {
                }
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item = menu?.findItem(R.id.action_favorite)
        if (isFavorited) {
            item?.setIcon(R.drawable.ic_favorite_24)
        } else {
            item?.setIcon(R.drawable.ic_favorite_border_24)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                viewModelFavorite.apply {
                    if (this@UserDetailActivity.isFavorited)
                        deleteFavorite(data)
                    else
                        addFavorite(data)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeUI() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setTitle(R.string.user_profile)
            elevation = 0F
        }
    }

    private fun initializeViewModel() {
        viewModel.apply {
            dataState.observe(this@UserDetailActivity, { dataState ->
                when (dataState) {
                    is DataState.Success<UserModel> -> {
                        displayProgressBar(false)
                        data = dataState.data
                        displayData()
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

            getUserDetail(username)
        }

        viewModelFavorite.isFavorited(username).observe(this, {
            if (it != isFavorited) {
                invalidateOptionsMenu()
            }
            isFavorited = it
        })

        viewModelFollow.getFollowersFollowing(username)
    }

    private fun initializeTabView() {
        binding.viewPager.adapter = FollowPagerAdapter(this)

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    @SuppressLint("SetTextI18n")
    private fun displayData() {
        binding.apply {
            userName.text = data.name
            userAccount.text = data.login
            userCompany.text = data.company
            userLocation.text = data.location
            totalRepo.text = data.publicRepos.toString()
            totalFollowers.text = data.followers.toString()
            totalFollowing.text = data.following.toString()

            Picasso.get()
                .load(data.avatarUrl)
                .placeholder(R.drawable.avatar_placeholder)
                .error(R.drawable.error_image)
                .into(userImage)
        }

        invalidateOptionsMenu()
        dataOnClickAction()
    }

    private fun displayError(error: String) {
        binding.errorContainer.apply {
            root.visibility = View.VISIBLE
            errorDesc.text = error
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        binding.loadingContainer.apply {
            root.background =
                ContextCompat.getDrawable(this@UserDetailActivity, R.color.dark_blue_grey)
            root.visibility = if (isDisplayed) View.VISIBLE else View.GONE
        }
    }

    private fun dataOnClickAction() {
        val url = BuildConfig.BASE_URL.replace("api.", "").plus(data.login)
        binding.btnGithub.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                val browserIntent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(url)
                }
                startActivity(browserIntent)
            }
        }
    }
}