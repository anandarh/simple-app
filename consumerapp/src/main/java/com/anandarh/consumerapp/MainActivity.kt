package com.anandarh.consumerapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.anandarh.consumerapp.adapters.UsersRecyclerViewAdapter
import com.anandarh.consumerapp.databinding.ActivityMainBinding
import com.anandarh.consumerapp.models.UserListModel
import com.anandarh.consumerapp.utilities.DataState
import com.anandarh.consumerapp.utilities.ResourceProvider
import com.anandarh.consumerapp.viewmodel.FavoriteViewModel
import com.anandarh.consumerapp.viewmodel.FavoriteViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdapter: UsersRecyclerViewAdapter

    private val viewModel: FavoriteViewModel by viewModels(
        factoryProducer = { FavoriteViewModelFactory(ResourceProvider(this)) }
    )

    private var usersData: UserListModel = UserListModel(items = listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeUI()

        viewModel.dataState.observe(this, { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    usersData = dataState.data
                    mAdapter.updateData(usersData)
                    displayProgressBar(false)
                    displayEmpty(usersData.items.isEmpty())
                }
                is DataState.Error -> {
                    displayError(dataState.exception.toString())
                    displayProgressBar(false)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })

        viewModel.getFavorites()

    }

    private fun initializeUI() {
        mAdapter = UsersRecyclerViewAdapter(usersData)
        binding.recyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        mAdapter.setOnItemClickListener(object : UsersRecyclerViewAdapter.ItemClickListener {
            override fun onItemClick(username: String) {
                val url = BuildConfig.BASE_URL.plus(username)
                val browserIntent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(url)
                }
                startActivity(browserIntent)
            }
        })
    }

    private fun displayEmpty(isDisplayed: Boolean) {
        binding.emptyContainer.visibility =
            if (isDisplayed) View.VISIBLE else View.GONE
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