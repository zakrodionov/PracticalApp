package com.zakrodionov.common.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.core.content.getSystemService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.shareIn

interface ConnectionService {
    fun observeConnectionState(): Flow<Boolean>
    fun hasConnection(): Boolean
}

class ConnectionServiceImpl(context: Context, dispatcher: CoroutineDispatcher) : ConnectionService {

    private val connectivityManager: ConnectivityManager? by lazy {
        context.applicationContext.getSystemService<ConnectivityManager>()
    }

    private val request: NetworkRequest by lazy {
        NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
    }

    private val connectionFlow = callbackFlow<Boolean> {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(true)
            }

            override fun onUnavailable() {
                trySend(false)
            }

            override fun onLost(network: Network) {
                trySend(false)
            }
        }
        connectivityManager?.registerNetworkCallback(request, networkCallback)

        awaitClose {
            connectivityManager?.unregisterNetworkCallback(networkCallback)
        }
    }.shareIn(
        scope = CoroutineScope(dispatcher),
        started = SharingStarted.WhileSubscribed(),
        replay = 1
    )

    override fun observeConnectionState(): Flow<Boolean> = connectionFlow

    override fun hasConnection(): Boolean {
        val connectedNetwork = connectivityManager?.isCurrentlyConnected()
        return connectedNetwork != null
    }

    private fun ConnectivityManager?.isCurrentlyConnected() = when (this) {
        null -> false
        else -> activeNetwork
            ?.let(::getNetworkCapabilities)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }
}
