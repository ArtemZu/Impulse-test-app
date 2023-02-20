package com.artemzu.githubapp.data.repository.repositories

import com.artemzu.database.entity.RepositoryDao
import com.artemzu.githubapp.data.model.Repository
import com.artemzu.githubapp.data.model.asRepository
import com.artemzu.githubapp.network.retrofit.repositories.RepositoriesNetwork
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoriesRepositoryImpl @Inject constructor(
    private val networkSource: RepositoriesNetwork,
    private val db: RepositoryDao
) : RepositoriesRepository {

    override suspend fun searchRepositories(
        searchText: String,
        page: Int
    ): Result<List<Repository>> {
        return try {
            Result.success(
                networkSource.searchRepositories(searchText, page)
                    .repositoryResponses.map { it.asRepository() })
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override fun getSavedRepositoriesIds(): Flow<List<Int>> = db.getCachedRepositoriesIds()
}