package com.artemzu.githubapp.domain.repositories

import com.artemzu.githubapp.data.repository.repositories.RepositoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedRepositoriesIds @Inject constructor(
    private val repository: RepositoriesRepository,
) {

    operator fun invoke(): Flow<List<Int>> = repository.getSavedRepositoriesIds()
}