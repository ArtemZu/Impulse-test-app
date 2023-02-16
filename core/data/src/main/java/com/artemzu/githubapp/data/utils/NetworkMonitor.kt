package com.artemzu.githubapp.data.utils

import kotlinx.coroutines.flow.Flow

/**
 * Utility for reporting app connectivity status
 */
interface NetworkMonitor {
    val isOnline: Flow<Status>
}

enum class Status {
    Available, Lost
}
