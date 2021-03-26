package com.zakrodionov.practicalapp.app.core

import com.zakrodionov.common.utils.net.NetworkHandler
import retrofit2.HttpException
import java.net.SocketTimeoutException

interface ErrorHandler {
    fun getError(throwable: Throwable): BaseError
}

class ErrorHandlerImpl(private val networkHandler: NetworkHandler) : ErrorHandler {

    @Suppress("ReturnCount")
    override fun getError(throwable: Throwable): BaseError {
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

    private fun withoutNetworkConnection() = !networkHandler.isConnected
}
