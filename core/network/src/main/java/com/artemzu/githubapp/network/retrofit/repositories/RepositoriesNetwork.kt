package com.artemzu.githubapp.network.retrofit.repositories

import com.artemzu.githubapp.network.model.RepositoriesResponse

interface RepositoriesNetwork {

    suspend fun searchRepositories(searchText: String, page: Int): RepositoriesResponse
}