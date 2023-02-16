package com.artemzu.githubapp.data.repository.history

import com.artemzu.database.entity.RepositoryDao
import com.artemzu.githubapp.data.model.Repository
import com.artemzu.githubapp.data.model.asRepositoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val db: RepositoryDao
) : HistoryRepository {

    override suspend fun saveWatchedRepository(repository: Repository) {
        db.insert(repository.asRepositoryEntity())
        db.removeOldData()
    }

    override fun getCachedRepositories(): Flow<List<Repository>> =
        db.getCachedRepositories().map { it.map { repository -> repository.asRepositoryEntity() } }
}