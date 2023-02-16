package com.artemzu.githubapp.data.repository.history

import com.artemzu.githubapp.data.model.Repository
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    suspend fun saveWatchedRepository(repository: Repository)

    fun getCachedRepositories(): Flow<List<Repository>>
}