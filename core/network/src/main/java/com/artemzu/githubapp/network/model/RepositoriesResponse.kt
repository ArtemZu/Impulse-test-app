package com.artemzu.githubapp.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoriesResponse(
    @SerialName("incomplete_results")
    val incompleteResults: Boolean,
    @SerialName("items")
    val repositoryResponses: List<RepositoryResponse>,
    @SerialName("total_count")
    val totalCount: Int
)

@Serializable
data class RepositoryResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String?,
    @SerialName("language")
    val language: String?,
    @SerialName("html_url")
    val url: String,
    @SerialName("owner")
    val owner: OwnerResponse,
)

@Serializable
data class OwnerResponse(
    @SerialName("avatar_url")
    val avatarUrl: String,
    @SerialName("login")
    val login: String
)