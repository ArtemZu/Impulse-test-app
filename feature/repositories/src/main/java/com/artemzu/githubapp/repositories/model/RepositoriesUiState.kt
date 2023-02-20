package com.artemzu.githubapp.repositories.model

import com.artemzu.githubapp.data.model.Repository
import com.artemzu.githubapp.ui.data.RepositoryUiItem

data class RepositoriesUiState(
    val searchText: String = "",
    val isLoading: Boolean = false,
    val repositoriesList: List<RepositoryUiItem> = emptyList(),
    val endReached: Boolean = false,
    val page: Int = 1
)

fun Repository.asRepositoryItem(): RepositoryUiItem =
    RepositoryUiItem(
        id = id,
        name = name,
        description = description,
        language = language,
        url = url,
        ownerName = ownerLogin,
        ownerAvatar = ownerAvatarUrl,
        isShown = isShown
    )

fun RepositoryUiItem.asRepositoryItem(): Repository =
    Repository(
        id = id,
        name = name,
        description = description,
        language = language,
        url = url,
        ownerLogin = ownerName,
        ownerAvatarUrl = ownerAvatar,
        isShown = null
    )