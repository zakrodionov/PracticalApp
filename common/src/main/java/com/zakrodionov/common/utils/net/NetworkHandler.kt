package com.zakrodionov.common.utils.net

import android.content.Context
import com.zakrodionov.common.extensions.networkInfo

class NetworkHandler(private val context: Context) {

    val isConnected get() = context.networkInfo?.isConnectedOrConnecting ?: false
}
