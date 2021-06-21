package com.zakrodionov.common.extensions

import android.os.Handler
import android.os.Looper
import com.zakrodionov.common.BuildConfig
import timber.log.Timber

val isDebug: Boolean
    get() = BuildConfig.DEBUG

inline fun <reified T : Any> T.debug(string: String) = Timber.d("Log__: $string")

inline fun <reified T : Any> T.checkThread(string: String = "") =
    debug("${Thread.currentThread().name}, is main = ${Looper.getMainLooper().isCurrentThread} $string")

fun Any?.toStringOrEmpty() = this?.let { "$this" } ?: ""

val Any.TAG get() = this.javaClass.simpleName

fun postDelayed(delay: Long, func: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed({ func.invoke() }, delay)
}
