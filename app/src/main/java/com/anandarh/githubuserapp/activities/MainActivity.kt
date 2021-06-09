package com.anandarh.githubuserapp.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.anandarh.githubuserapp.R
import com.anandarh.githubuserapp.adapters.UsersAdapter
import com.anandarh.githubuserapp.constants.IntentConstant.Companion.EXTRA_USER
import com.anandarh.githubuserapp.databinding.ActivityMainBinding
import com.anandarh.githubuserapp.models.GithubResponseModel
import com.anandarh.githubuserapp.models.UserModel
import com.anandarh.githubuserapp.utilities.DataState
import com.anandarh.githubuserapp.utilities.ResourceProvider
import com.anandarh.githubuserapp.viewmodels.UserStateEvent
import com.anandarh.githubuserapp.viewmodels.UserViewModel
import com.anandarh.githubuserapp.viewmodels.UserViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setItemClickAction()

        val viewModelFactory = UserViewModelFactory(ResourceProvider(this))
        viewModel = ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)
        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success<GithubResponseModel> -> {
                    displayProgressBar(false)
                    showData(dataState.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    TODO("HANDLE ERROR")
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }


    private fun displayProgressBar(isDisplayed: Boolean) {
        binding.progressBar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun showData(data: GithubResponseModel) {
        Log.d("RESAP", data.toString())
        usersAdapter = UsersAdapter(data)

        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = usersAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        setSearchView(menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setItemClickAction() {
        usersAdapter.setOnItemClickListener(object : UsersAdapter.ItemClickListener {
            override fun onItemClick(data: UserModel) {
                val objectIntent = Intent(this@MainActivity, UserDetailActivity::class.java)
                objectIntent.putExtra(EXTRA_USER, data)
                startActivity(objectIntent)
            }
        })
    }

    private fun setSearchView(menu: Menu) {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setStateEvent(UserStateEvent.GetSearchUserEvent(query.orEmpty()))
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }
}