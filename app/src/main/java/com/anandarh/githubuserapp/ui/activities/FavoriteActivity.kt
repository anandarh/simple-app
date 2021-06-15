package com.anandarh.githubuserapp.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.anandarh.githubuserapp.databinding.ActivityFavoriteBinding
import com.anandarh.githubuserapp.utilities.DataState
import com.anandarh.githubuserapp.utilities.ResourceProvider
import com.anandarh.githubuserapp.viewmodels.FavoriteViewModel
import com.anandarh.githubuserapp.viewmodels.FavoriteViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModelFactory: FavoriteViewModelFactory
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelFactory = FavoriteViewModelFactory(ResourceProvider(this))
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavoriteViewModel::class.java)

        viewModel.dataState.observe(this, { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    binding.text.text = dataState.data.toString()
                }
                is DataState.Error -> {
                    println(dataState.exception.toString())
                }
                DataState.Loading -> {
                    println("LOADING")
                }
            }
        })

        viewModel.getFavorites()
    }
}