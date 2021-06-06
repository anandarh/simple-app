package com.anandarh.githubuserapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.anandarh.githubuserapp.adapters.UsersAdapter
import com.anandarh.githubuserapp.constants.IntentConstant.Companion.EXTRA_USER
import com.anandarh.githubuserapp.databinding.ActivityMainBinding
import com.anandarh.githubuserapp.models.UserModel
import com.anandarh.githubuserapp.repositories.UserRepository


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeUI()
        setItemClickAction()
    }

    private fun initializeUI() {
        usersAdapter = UsersAdapter(UserRepository(this@MainActivity).getUsers())

        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = usersAdapter
        }
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
}