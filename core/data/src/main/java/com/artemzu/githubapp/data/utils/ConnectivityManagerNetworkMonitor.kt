package com.artemzu.githubapp.data.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest.Builder
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConnectivityManagerNetworkMonitor @Inject constructor(
    @ApplicationContext private val context: Context,
) : NetworkMonitor {
    override val isOnline: Flow<Status> = callbackFlow {
        val connectivityManager = context.getSystemService<ConnectivityManager>()

        /**
         * The callback's methods are invoked on changes to *any* network, not just the active
         * network. So to check for network connectivity, one must query the active network of the
         * ConnectivityManager.
         */
        val callback = object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                val status = if (connectivityManager.isCurrentlyConnected()) {
                    Status.Available
                } else {
                    Status.Lost
                }
                launch { send(status) }
            }

            override fun onLost(network: Network) {
                launch { send(Status.Lost) }
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                val status = if (connectivityManager.isCurrentlyConnected()) {
                    Status.Available
                } else {
                    Status.Lost
                }
                launch { send(status) }
            }
        }

        connectivityManager?.registerNetworkCallback(
            Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build(),
            callback,
        )

        val status = if (connectivityManager.isCurrentlyConnected()) {
            Status.Available
        } else {
            Status.Lost
        }
        launch { send(status) }

        awaitClose {
            connectivityManager?.unregisterNetworkCallback(callback)
        }
    }

    private fun ConnectivityManager?.isCurrentlyConnected() = when (this) {
        null -> false
        else -> activeNetwork
            ?.let(::getNetworkCapabilities)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }
}
