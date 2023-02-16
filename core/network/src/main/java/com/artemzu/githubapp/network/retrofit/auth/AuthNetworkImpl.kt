package com.artemzu.githubapp.network.retrofit.auth

import com.artemzu.githubapp.network.model.TokenResponse
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

interface AuthNetworkApi {

    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    suspend fun getAccessToken(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("code") code: String
    ): TokenResponse
}

@Singleton
class AuthNetworkImpl @Inject constructor(
    private val networkApi: AuthNetworkApi
) : AuthNetwork {

    override suspend fun getToken(
        clientId: String,
        clientSecret: String,
        code: String
    ): TokenResponse {
        return networkApi.getAccessToken(
            clientId = clientId,
            clientSecret = clientSecret,
            code = code
        )
    }
}