package com.anandarh.githubuserapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anandarh.githubuserapp.constants.IntentConstant.Companion.EXTRA_USER
import com.anandarh.githubuserapp.databinding.ActivityUserDetailBinding
import com.anandarh.githubuserapp.models.UserModel
import com.bumptech.glide.Glide


class UserDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var user: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getParcelableExtra<UserModel>(EXTRA_USER) as UserModel

        setData()
        setOnClickAction()
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {

        val imageResource: Int = this@UserDetailActivity.resources.getIdentifier(
            user.avatar_url,
            null,
            this@UserDetailActivity.packageName
        )

        Glide.with(this@UserDetailActivity).load(imageResource).into(binding.userImage)
        binding.userName.text = user.name
        binding.userAccount.text = "@${user.login}"
        binding.userCompany.text = user.company
        binding.userLocation.text = user.location
        binding.totalRepo.text = user.public_repos.toString()
        binding.totalFollowers.text = user.followers.toString()
        binding.totalFollowing.text = user.following.toString()
    }

    private fun setOnClickAction() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnGithub.setOnClickListener {
            val browserIntent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse("https://github.com/${user.login}")
            }
            startActivity(browserIntent)
        }

        binding.btnShare.setOnClickListener {
            val share = Intent.createChooser(Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "https://github.com/${user.login}")
                type = "text/plain"
            }, null)
            startActivity(share)
        }
    }
}