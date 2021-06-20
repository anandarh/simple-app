package com.anandarh.githubuserapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anandarh.githubuserapp.R
import com.anandarh.githubuserapp.adapters.UsersRecyclerViewAdapter
import com.anandarh.githubuserapp.constants.IntentConstant
import com.anandarh.githubuserapp.databinding.ActivityFavoriteBinding
import com.anandarh.githubuserapp.models.UserListModel
import com.anandarh.githubuserapp.utilities.DataState
import com.anandarh.githubuserapp.utilities.ResourceProvider
import com.anandarh.githubuserapp.utilities.SwipeToDeleteCallback
import com.anandarh.githubuserapp.viewmodels.FavoriteViewModel
import com.anandarh.githubuserapp.viewmodels.FavoriteViewModelFactory
import com.google.android.material.snackbar.Snackbar

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var mAdapter: UsersRecyclerViewAdapter
    private val viewModel: FavoriteViewModel by viewModels(
        factoryProducer = { FavoriteViewModelFactory(ResourceProvider(this)) }
    )
    private var usersData: UserListModel = UserListModel(items = listOf())

    private var isFirstLoaded = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeUI()

        viewModel.dataState.observe(this, { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    usersData = dataState.data
                    mAdapter.updateData(usersData)
                    displayProgressBar(false)

                    if (!isFirstLoaded) {
                        Snackbar.make(binding.root, R.string.has_deleted, Snackbar.LENGTH_LONG)
                            .show()
                    }
                    isFirstLoaded = false
                }
                is DataState.Error -> {
                    displayError(dataState.exception.toString())
                    displayProgressBar(false)
                }
                DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
        viewModel.getFavorites()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun initializeUI() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setTitle(R.string.favorite)
        }

        mAdapter = UsersRecyclerViewAdapter(usersData)
        binding.recyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
        }

        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteFavorite(usersData.items[viewHolder.adapterPosition])
            }
        }

        ItemTouchHelper(swipeHandler).attachToRecyclerView(binding.recyclerView)

        mAdapter.setOnItemClickListener(object : UsersRecyclerViewAdapter.ItemClickListener {
            override fun onItemClick(username: String) {
                val objectIntent = Intent(this@FavoriteActivity, UserDetailActivity::class.java)
                objectIntent.putExtra(IntentConstant.EXTRA_USERNAME, username)
                startActivity(objectIntent)
            }
        })
    }

    private fun displayError(error: String) {
        binding.errorContainer.apply {
            root.visibility = View.VISIBLE
            errorDesc.text = error
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        binding.loadingContainer.apply {
            root.visibility = if (isDisplayed) View.VISIBLE else View.GONE
        }
    }
}