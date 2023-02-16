package com.artemzu.githubapp.data.repository.auth

import com.artemzu.githubapp.data.model.TokenData
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getSavedCode(): Flow<String?>
    suspend fun saveCode(code: String)
    fun getSavedToken(): Flow<String?>
    suspend fun saveToken(token: String)
    suspend fun removeCode()
    suspend fun getToken(clientId: String, clientSecret: String, code: String): TokenData
    suspend fun logout()
}