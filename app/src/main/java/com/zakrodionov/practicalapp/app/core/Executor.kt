package com.zakrodionov.practicalapp.app.core

import com.zakrodionov.practicalapp.app.core.dispatchers.DispatchersProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface Executor {
    suspend fun <T : Any> execute(block: suspend () -> T): Result<T>
}

class ExecutorImpl @Inject constructor (
    private val errorHandler: ErrorHandler,
    private val dispatchersProvider: DispatchersProvider
) : Executor {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun <T : Any> execute(block: suspend () -> T): Result<T> =
        withContext(dispatchersProvider.main) {
            try {
                val result = withContext(dispatchersProvider.io) { block.invoke() }
                Result.Success(result)
            } catch (e: Exception) {
                Result.Failure(errorHandler.getError(e))
            }
        }
}
