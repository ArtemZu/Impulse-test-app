package com.artemzu.githubapp.domain.utils

import com.artemzu.githubapp.data.utils.NetworkMonitor
import com.artemzu.githubapp.data.utils.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IsDeviceOnlineUseCase @Inject constructor(
    private val networkMonitor: NetworkMonitor
) {

    operator fun invoke(): Flow<Boolean> {
        return networkMonitor.isOnline
            .distinctUntilChanged()
            .map { it == Status.Available }
    }
}