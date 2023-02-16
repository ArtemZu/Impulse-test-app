package com.artemzu.githubapp.data.repository.auth

import com.artemzu.datastore.AuthDataStore
import com.artemzu.githubapp.data.model.asTokenData
import com.artemzu.githubapp.network.retrofit.auth.AuthNetwork
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataStore: AuthDataStore,
    private val networkSource: AuthNetwork
) : AuthRepository {
    override fun getSavedCode(): Flow<String?> = dataStore.getCodeFlow()

    override suspend fun saveCode(code: String) {
        dataStore.saveCode(code)
    }

    override fun getSavedToken(): Flow<String?> = dataStore.getTokenFlow()

    override suspend fun saveToken(token: String) {
        dataStore.saveToken(token)
    }

    override suspend fun removeCode() {
        dataStore.removeCode()
    }

    override suspend fun getToken(
        clientId: String,
        clientSecret: String,
        code: String
    ) = networkSource.getToken(clientId, clientSecret, code).asTokenData()

    override suspend fun logout() {
        dataStore.removeAllData()
    }
}