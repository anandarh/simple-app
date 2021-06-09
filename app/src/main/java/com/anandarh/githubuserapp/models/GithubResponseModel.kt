package com.anandarh.githubuserapp.models


import com.google.gson.annotations.SerializedName

data class GithubResponseModel(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    val items: List<GithubItemModel>,
    @SerializedName("total_count")
    val totalCount: Int,
    val userListModel: UserListModel?
)