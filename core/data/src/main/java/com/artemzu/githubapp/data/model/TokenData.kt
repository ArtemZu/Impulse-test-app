package com.artemzu.githubapp.data.model

import com.artemzu.githubapp.network.model.TokenResponse

data class TokenData(
    val token: String? = null,
    val errorMessage: String? = null
)

fun TokenResponse.asTokenData() =
    TokenData(
        token = token,
        errorMessage = errorDescription
    )
