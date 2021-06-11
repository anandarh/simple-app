package com.anandarh.githubuserapp.ui.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.anandarh.githubuserapp.R
import com.anandarh.githubuserapp.adapters.UsersRecyclerViewAdapter
import com.anandarh.githubuserapp.constants.IntentConstant.Companion.EXTRA_USERNAME
import com.anandarh.githubuserapp.databinding.ActivityMainBinding
import com.anandarh.githubuserapp.models.GithubResponseModel
import com.anandarh.githubuserapp.utilities.DataState
import com.anandarh.githubuserapp.utilities.ResourceProvider
import com.anandarh.githubuserapp.viewmodels.UserStateEvent
import com.anandarh.githubuserapp.viewmodels.UserViewModelFactory
import com.anandarh.githubuserapp.viewmodels.UsersViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var usersAdapter: UsersRecyclerViewAdapter
    private lateinit var viewModel: UsersViewModel
    private lateinit var data: GithubResponseModel

    private var mQuery: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = UserViewModelFactory(ResourceProvider(this))
        viewModel = ViewModelProvider(this, viewModelFactory).get(UsersViewModel::class.java)

        subscribeObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        setSearchView(menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun subscribeObserver() {
        viewModel.apply {
            dataState.observe(this@MainActivity, { dataState ->
                when (dataState) {
                    is DataState.Success<GithubResponseModel> -> {
                        data = dataState.data
                        displayProgressBar(false)
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

            searchQuery.observe(this@MainActivity, {
                mQuery = it
            })
        }
    }

    private fun displayData() {
        usersAdapter = UsersRecyclerViewAdapter(data)

        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = usersAdapter
        }

        setItemClickAction()
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

    private fun setItemClickAction() {
        usersAdapter.setOnItemClickListener(object : UsersRecyclerViewAdapter.ItemClickListener {
            override fun onItemClick(username: String) {
                val objectIntent = Intent(this@MainActivity, UserDetailActivity::class.java)
                objectIntent.putExtra(EXTRA_USERNAME, username)
                startActivity(objectIntent)
            }
        })
    }

    private fun setSearchView(menu: Menu) {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        val closeButton = searchView.findViewById(R.id.search_close_btn) as ImageView

        menu.findItem(R.id.search)
            .setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                    searchView.apply {
                        onActionViewExpanded()
                        setQuery(mQuery, false)
                    }
                    return true
                }

                override fun onMenuItemActionCollapse(item: MenuItem?) = true
            })

        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = resources.getString(R.string.search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.setSearchQuery(query.orEmpty())
                    viewModel.setStateEvent(UserStateEvent.GetSearchUserEvent(query.orEmpty()))
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }

        closeButton.setOnClickListener {
            if (!data.items.isNullOrEmpty()) {
                viewModel.apply {
                    setSearchQuery("")
                    setStateEvent(UserStateEvent.GetUsersEvent)
                }
            }
            searchView.setQuery("", false)
        }
    }
}