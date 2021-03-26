package com.zakrodionov.common.utils.net

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.MutableLiveData

class ConnectivityListener(private val context: Context) {

    val isConnected = MutableLiveData<Boolean>()

    //region used on api < 24
    private val intentFilter by lazy {
        IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
    }

    private val networkBroadcastReceiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val isConn = isConnected()
                isConnected.postValue(isConn)
            }
        }
    }
    //endregion

    private val networkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isConnected.postValue(true)
            }

            override fun onLost(network: Network) {
                isConnected.postValue(false)
            }
        }
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getConnectivityManager()?.registerDefaultNetworkCallback(networkCallback)
        } else {
            context.registerReceiver(networkBroadcastReceiver, intentFilter)
        }
    }

    fun isConnected(): Boolean {
        val activeNetwork = getConnectivityManager()?.activeNetworkInfo
        val isConnected = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }

    private fun getConnectivityManager() = getSystemService(context, ConnectivityManager::class.java)
}
