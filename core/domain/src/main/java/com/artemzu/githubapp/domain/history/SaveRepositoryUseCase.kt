package com.artemzu.githubapp.domain.history

import com.artemzu.githubapp.common.network.AppDispatchers
import com.artemzu.githubapp.common.network.Dispatcher
import com.artemzu.githubapp.data.model.Repository
import com.artemzu.githubapp.data.repository.history.HistoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveRepositoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(repository: Repository) {
        withContext(ioDispatcher) {
            historyRepository.saveWatchedRepository(repository)
        }
    }
}