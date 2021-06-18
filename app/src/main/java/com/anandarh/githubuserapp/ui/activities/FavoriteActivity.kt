package com.anandarh.githubuserapp.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anandarh.githubuserapp.R
import com.anandarh.githubuserapp.adapters.UsersRecyclerViewAdapter
import com.anandarh.githubuserapp.databinding.ActivityFavoriteBinding
import com.anandarh.githubuserapp.models.UserListModel
import com.anandarh.githubuserapp.utilities.DataState
import com.anandarh.githubuserapp.utilities.ResourceProvider
import com.anandarh.githubuserapp.utilities.SwipeToDeleteCallback
import com.anandarh.githubuserapp.viewmodels.FavoriteViewModel
import com.anandarh.githubuserapp.viewmodels.FavoriteViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var mAdapter: UsersRecyclerViewAdapter
    private val viewModel: FavoriteViewModel by viewModels(
        factoryProducer = { FavoriteViewModelFactory(ResourceProvider(this)) }
    )
    private var usersData: UserListModel = UserListModel(items = listOf())

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
                }
                is DataState.Error -> {
                    println(dataState.exception.toString())
                }
                DataState.Loading -> {
                    println("LOADING")
                }
            }
        })
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
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}