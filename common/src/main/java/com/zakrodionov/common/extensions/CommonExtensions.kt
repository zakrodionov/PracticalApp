package com.zakrodionov.common.extensions

import android.os.Handler
import android.os.Looper
import com.zakrodionov.common.BuildConfig
import timber.log.Timber

val isDebug: Boolean
    get() = BuildConfig.DEBUG

fun debug(string: String) = Timber.d(string)

fun Any?.toStringOrEmpty() = this?.let { "$this" } ?: ""

val Any.TAG get() = this.javaClass.simpleName

fun postDelayed(delay: Long, func: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed({ func.invoke() }, delay)
}
