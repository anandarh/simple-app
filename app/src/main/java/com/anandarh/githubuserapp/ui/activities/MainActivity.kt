package com.anandarh.githubuserapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.anandarh.githubuserapp.R
import com.anandarh.githubuserapp.adapters.UsersRecyclerViewAdapter
import com.anandarh.githubuserapp.constants.IntentConstant.Companion.EXTRA_USERNAME
import com.anandarh.githubuserapp.databinding.ActivityMainBinding
import com.anandarh.githubuserapp.factories.UserViewModelFactory
import com.anandarh.githubuserapp.models.UserListModel
import com.anandarh.githubuserapp.utilities.DataState
import com.anandarh.githubuserapp.utilities.ResourceProvider
import com.anandarh.githubuserapp.viewmodels.UserStateEvent
import com.anandarh.githubuserapp.viewmodels.UsersViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var usersAdapter: UsersRecyclerViewAdapter
    private lateinit var viewModel: UsersViewModel
    private lateinit var data: UserListModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.elevation = 0F

        val viewModelFactory = UserViewModelFactory(ResourceProvider(this))
        viewModel = ViewModelProvider(this, viewModelFactory).get(UsersViewModel::class.java)

        subscribeObserver()
        setSearchView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
            }

            R.id.action_language -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }

            R.id.action_reminder -> {
                startActivity(Intent(this, ReminderActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun subscribeObserver() {
        viewModel.apply {
            dataState.observe(this@MainActivity, { dataState ->
                when (dataState) {
                    is DataState.Success<UserListModel> -> {
                        data = dataState.data
                        displayProgressBar(false)
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

            searchQuery.observe(this@MainActivity, {
                binding.search.editText?.setText(it)
            })
        }
    }

    private fun setSearchView() {
        binding.search.apply {
            editText?.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
                val query = editText?.text.toString()
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.setSearchQuery(query)
                    viewModel.setStateEvent(UserStateEvent.GetSearchUserEvent(query))
                    return@OnEditorActionListener true
                }
                false
            })

            setEndIconOnClickListener {
                if (!data.items.isNullOrEmpty()) {
                    viewModel.apply {
                        setSearchQuery("")
                        setStateEvent(UserStateEvent.GetUsersEvent)
                    }
                }
            }
        }
    }

    private fun displayData() {
        usersAdapter = UsersRecyclerViewAdapter(data)

        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = usersAdapter
        }

        usersAdapter.setOnItemClickListener(object : UsersRecyclerViewAdapter.ItemClickListener {
            override fun onItemClick(username: String) {
                val objectIntent = Intent(this@MainActivity, UserDetailActivity::class.java)
                objectIntent.putExtra(EXTRA_USERNAME, username)
                startActivity(objectIntent)
            }
        })
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