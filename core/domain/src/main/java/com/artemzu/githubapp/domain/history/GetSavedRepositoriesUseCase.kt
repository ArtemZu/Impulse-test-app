package com.artemzu.githubapp.domain.history

import com.artemzu.githubapp.data.model.Repository
import com.artemzu.githubapp.data.repository.history.HistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedRepositoriesUseCase @Inject constructor(
    private val repository: HistoryRepository
) {

    operator fun invoke(): Flow<List<Repository>> = repository.getCachedRepositories()
}