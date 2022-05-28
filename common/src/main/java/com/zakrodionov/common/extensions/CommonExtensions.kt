package com.zakrodionov.common.extensions

import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import com.zakrodionov.common.BuildConfig
import timber.log.Timber

val isDebug: Boolean
    get() = BuildConfig.DEBUG

inline fun <reified T : Any> T.debug(string: String) = Timber.d("Log__debug: $string")

inline fun <reified T : Any> T.checkThread(string: String = "") =
    debug("${Thread.currentThread().name}, is main = ${Looper.getMainLooper().isCurrentThread} $string")

fun Any?.toStringOrEmpty(): String = this?.let { "$this" } ?: ""

fun Any?.toStringOrNull(): String = this?.let { "$this" } ?: "null"

val Any.TAG get() = this.javaClass.simpleName

fun postDelayed(delay: Long, func: () -> Unit): Runnable {
    return Handler(Looper.getMainLooper()).postDelayed(delay, func)
}

fun Handler.postDelayed(delay: Long, func: () -> Unit): Runnable {
    val runnable = Runnable { func.invoke() }
    postDelayed(runnable, delay)
    return runnable
}

fun preferenceListener(
    propertyKey: String,
    propertyUpdated: (key: String) -> Unit
): SharedPreferences.OnSharedPreferenceChangeListener {
    return SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (propertyKey == key) {
            propertyUpdated.invoke(key)
        }
    }
}
