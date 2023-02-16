package com.artemzu.githubapp.domain.auth

import com.artemzu.githubapp.data.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IsUserAuthorizedUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return authRepository.getSavedToken().map { it != null }
    }
}