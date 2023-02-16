package com.artemzu.githubapp.domain.auth

import com.artemzu.githubapp.data.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class GetAuthCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(): Flow<String?> = authRepository.getSavedCode().distinctUntilChanged()
}