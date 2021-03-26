package com.zakrodionov.common.extensions

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import kotlin.reflect.KClass

@Suppress("SwallowedException", "TooGenericExceptionCaught")
suspend fun <T> asyncOrNull(block: suspend () -> T?): Deferred<T?> =
    coroutineScope {
        async {
            try {
                block.invoke()
            } catch (e: Exception) {
                null
            }
        }
    }

@Suppress("SwallowedException", "TooGenericExceptionCaught")
inline fun tryOrIgnore(action: () -> Unit) {
    try {
        action()
    } catch (ignore: Exception) {
        Timber.e(ignore)
        // ignore
    }
}

@Suppress("SwallowedException", "TooGenericExceptionCaught")
inline fun <T> tryOrNull(action: () -> T): T? =
    try {
        action()
    } catch (e: Exception) {
        Timber.e(e)
        null
    }

@Suppress("SwallowedException", "TooGenericExceptionCaught")
fun <T> tryOrReturnDefault(block: () -> T, default: () -> T): T = try {
    block.invoke()
} catch (e: Exception) {
    Timber.e(e)
    default.invoke()
}

// Удобно использовать для запросов с 204 кодом
fun <T> Response<T>.successOrThrow(): Response<T> {
    if (isSuccessful) {
        return this
    } else {
        throw HttpException(this)
    }
}

@Suppress("MagicNumber")
fun <T> Response<T>.successOrHandle403Msg(): String? {
    return when {
        isSuccessful -> {
            null
        }
        code() == 403 -> {
            val errorString = errorBody()?.string()
            val json = errorString.safelyParseJson()
            val message = json.getStringField("error")
            if (message.isBlank()) {
                throw HttpException(this)
            } else {
                message
            }
        }
        else -> {
            throw HttpException(this)
        }
    }
}

inline fun <T : Any> T?.ifNotNull(block: (T) -> Unit) {
    if (this != null) {
        block.invoke(this)
    }
}

inline fun <T : Any> T?.ifNotNullAndIf(predicate: (T) -> Boolean, block: (T) -> Unit) {
    if (this != null && predicate.invoke(this)) {
        block.invoke(this)
    }
}

inline fun <reified T : Enum<*>> KClass<T>.safeValueOf(rawValue: String, default: T): T =
    java.enumConstants?.find { it.name.equals(rawValue, true) } ?: default

inline fun <reified T : Enum<T>> String.asEnumOrDefault(defaultValue: T): T =
    enumValues<T>().find { it.name.equals(this, true) } ?: defaultValue
