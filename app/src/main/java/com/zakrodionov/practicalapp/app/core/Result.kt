package com.zakrodionov.practicalapp.app.core

sealed class Result<T : Any> {
    data class Success<T : Any>(val data: T) : Result<T>()
    data class Failure<T : Any>(val baseError: BaseError) : Result<T>()

    fun exceptionOrNull(): BaseError? =
        when (this) {
            is Failure -> baseError
            else -> null
        }
}

inline fun <T : Any> Result<T>.onFailure(action: (baseError: BaseError) -> Unit): Result<T> {
    exceptionOrNull()?.let { action(it) }
    return this
}

inline fun <T : Any> Result<T>.onSuccess(action: (value: T) -> Unit): Result<T> {
    if (this is Result.Success) action(data)
    return this
}
