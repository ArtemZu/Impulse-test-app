package com.artemzu.githubapp.ui.data

data class RepositoryUiItem(
    val id: Int,
    val name: String,
    val description: String?,
    val language: String?,
    val url: String,
    val ownerName: String,
    val ownerAvatar: String,
    val isShown: Boolean?
)