package com.artemzu.githubapp.data.repository.repositories

import com.artemzu.githubapp.data.model.Repository

interface RepositoriesRepository {

    suspend fun searchRepositories(searchText: String, page: Int): Result<List<Repository>>
}