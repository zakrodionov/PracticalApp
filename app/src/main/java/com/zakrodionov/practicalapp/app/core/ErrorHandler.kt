package com.zakrodionov.practicalapp.app.core

import com.zakrodionov.common.utils.net.ConnectionService
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException

interface ErrorHandler {
    fun getError(throwable: Throwable): BaseError
}

class ErrorHandlerImpl(private val connectionService: ConnectionService) : ErrorHandler {

    @Suppress("ReturnCount")
    override fun getError(throwable: Throwable): BaseError {
        Timber.e(throwable)
        when {
            withoutNetworkConnection() -> {
                return NetworkConnectionError()
            }

            throwable is HttpException -> {
                return HttpError()
            }

            throwable is SocketTimeoutException -> {
                return HttpError()
            }

            else -> UnknownError()
        }

        return UnknownError()
    }

    private fun withoutNetworkConnection() = !connectionService.hasConnection()
}
