package com.zakrodionov.practicalapp.app.core

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Failure(val baseError: BaseError) : Result<Nothing>()

    fun exceptionOrNull(): BaseError? =
        when (this) {
            is Failure -> baseError
            else -> null
        }
}

inline fun <T> Result<T>.onFailure(action: (baseError: BaseError) -> Unit): Result<T> {
    exceptionOrNull()?.let { action(it) }
    return this
}

inline fun <T> Result<T>.onSuccess(action: (value: T) -> Unit): Result<T> {
    if (this is Result.Success) action(data)
    return this
}

suspend fun <INPUT : Any, OUTPUT : Any> Result<INPUT>.then(block: suspend (INPUT) -> Result<OUTPUT>): Result<OUTPUT> =
    when (this) {
        is Result.Success -> block.invoke(data)
        is Result.Failure -> Result.Failure(baseError)
    }
