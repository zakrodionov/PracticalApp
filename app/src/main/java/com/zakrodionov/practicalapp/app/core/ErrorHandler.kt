package com.zakrodionov.practicalapp.app.core

import com.zakrodionov.common.net.ConnectionService
import retrofit2.HttpException
import timber.log.Timber
import java.net.ConnectException
import java.net.ProtocolException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface ErrorHandler {
    fun getError(throwable: Throwable): BaseError
}

class ErrorHandlerImpl(private val connectionService: ConnectionService) : ErrorHandler {

    @Suppress("ReturnCount")
    override fun getError(throwable: Throwable): BaseError {
        Timber.e(throwable)
        when {
            withoutNetworkConnection() || throwable.isNetworkException() -> {
                return NetworkConnectionError()
            }

            throwable is HttpException -> {
                return HttpError()
            }

            else -> UnknownError()
        }

        return UnknownError()
    }

    private fun withoutNetworkConnection() = !connectionService.hasConnection()

    private fun Throwable?.isNetworkException(): Boolean {
        return when (this) {
            is ConnectException,
            is SocketException,
            is SocketTimeoutException,
            is UnknownHostException,
            is ProtocolException,
            -> true
            else -> false
        }
    }
}
