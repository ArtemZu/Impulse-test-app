package com.artemzu.githubapp.network.retrofit.auth

import com.artemzu.githubapp.network.model.TokenResponse

interface AuthNetwork {
    suspend fun getToken(clientId: String, clientSecret: String, code: String): TokenResponse
}