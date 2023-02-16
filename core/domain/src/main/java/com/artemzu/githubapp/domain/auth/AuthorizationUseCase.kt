package com.artemzu.githubapp.domain.auth

import com.artemzu.githubapp.common.network.AppDispatchers.*
import com.artemzu.githubapp.common.network.Dispatcher
import com.artemzu.githubapp.data.repository.auth.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthorizationUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(
        clientId: String,
        clientSecret: String,
        code: String
    ): RequestStatus {
        return withContext(ioDispatcher) {
            authRepository.removeCode()
            try {
                val tokenData = authRepository.getToken(
                    clientId = clientId,
                    clientSecret = clientSecret,
                    code = code,
                )
                return@withContext when {
                    tokenData.token != null -> {
                        authRepository.saveToken(tokenData.token!!)
                        RequestStatus.Success
                    }

                    tokenData.errorMessage != null ->
                        RequestStatus.ErrorWithMessage(tokenData.errorMessage!!)

                    else -> RequestStatus.CommonError
                }
            } catch (e: Exception) {
                return@withContext RequestStatus.CommonError
            }
        }
    }

    sealed interface RequestStatus {
        data class ErrorWithMessage(val errorMessage: String) : RequestStatus
        object CommonError : RequestStatus
        object Success : RequestStatus
    }
}