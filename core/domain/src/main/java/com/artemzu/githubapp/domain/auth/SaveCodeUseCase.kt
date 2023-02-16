package com.artemzu.githubapp.domain.auth

import android.net.Uri
import com.artemzu.githubapp.data.repository.auth.AuthRepository
import javax.inject.Inject

class SaveCodeUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(uri: Uri?) {
        uri?.getQueryParameter("code")?.let {
            repository.saveCode(it)
        }
    }
}