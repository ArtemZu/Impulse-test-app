package com.artemzu.githubapp.data.model

import com.artemzu.database.entity.RepositoryEntity
import com.artemzu.githubapp.network.model.RepositoryResponse

data class Repository(
    val id: Int,
    val name: String,
    val description: String?,
    val language: String?,
    val url: String,
    val ownerAvatarUrl: String,
    val ownerLogin: String,
    val isShown: Boolean?
)

fun RepositoryResponse.asRepository(): Repository =
    Repository(
        id = id,
        name = name,
        description = description,
        language = language,
        url = url,
        ownerAvatarUrl = owner.avatarUrl,
        ownerLogin = owner.login,
        isShown = null
    )

fun Repository.asRepositoryEntity(): RepositoryEntity =
    RepositoryEntity(
        id = id,
        name = name,
        description = description,
        language = language,
        url = url,
        ownerAvatarUrl = ownerAvatarUrl,
        ownerLogin = ownerLogin
    )

fun RepositoryEntity.asRepositoryEntity(): Repository =
    Repository(
        id = id,
        name = name,
        description = description,
        language = language,
        url = url,
        ownerAvatarUrl = ownerAvatarUrl,
        ownerLogin = ownerLogin,
        isShown = null
    )
