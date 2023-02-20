package com.artemzu.githubapp.data.repository.repositories

import com.artemzu.githubapp.data.model.Repository
import kotlinx.coroutines.flow.Flow

interface RepositoriesRepository {

    suspend fun searchRepositories(searchText: String, page: Int): Result<List<Repository>>

    fun getSavedRepositoriesIds(): Flow<List<Int>>
}