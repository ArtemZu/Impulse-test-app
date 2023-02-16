package com.artemzu.githubapp.domain.repositories

import com.artemzu.githubapp.common.network.AppDispatchers
import com.artemzu.githubapp.common.network.Dispatcher
import com.artemzu.githubapp.data.model.Repository
import com.artemzu.githubapp.data.repository.repositories.RepositoriesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchRepositoriesUseCase @Inject constructor(
    private val repository: RepositoriesRepository,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(searchText: String, page: Int): Result<List<Repository>> {
        return withContext(ioDispatcher) {
            val firstRequest = async {
                repository.searchRepositories(searchText, page)
            }
            val secondRequest = async {
                repository.searchRepositories(searchText, page + 1)
            }

            val firstResponse = firstRequest.await()
            val secondResponse = secondRequest.await()

            if (firstResponse.isFailure && secondResponse.isFailure) {
                return@withContext secondResponse
            }

            val firstData = firstResponse.getOrNull()
            val secondData = secondResponse.getOrNull()

            return@withContext Result.success(firstData.orEmpty() + secondData.orEmpty())
        }
    }
}