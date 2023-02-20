package com.artemzu.githubapp.history.model

import com.artemzu.githubapp.data.model.Repository
import com.artemzu.githubapp.ui.data.RepositoryUiItem

data class HistoryUiState(
    val isLoading: Boolean = true,
    val historyList: List<RepositoryUiItem> = emptyList()
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
        isShown = null
    )