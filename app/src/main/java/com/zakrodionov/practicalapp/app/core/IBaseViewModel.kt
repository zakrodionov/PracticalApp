package com.zakrodionov.practicalapp.app.core

import com.zakrodionov.common.ui.ShowAction
import com.zakrodionov.practicalapp.app.core.ImportanceError.CONTENT_ERROR
import com.zakrodionov.practicalapp.app.core.ImportanceError.CRITICAL_ERROR
import com.zakrodionov.practicalapp.app.core.ImportanceError.NON_CRITICAL_ERROR
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface IBaseViewModel<STATE : Any, EVENT : Any> {

    val stateFlow: StateFlow<STATE>

    val eventFlow: SharedFlow<EVENT>

    // Общие ShowEvent события для toast, alert, dialog
    val showEventFlow: SharedFlow<ShowEvent>

    fun getInitialState(): STATE

    val state: STATE get() = stateFlow.value

    suspend fun reduce(state: suspend () -> STATE)

    suspend fun postEvent(event: EVENT)

    suspend fun postShowEvent(showEvent: ShowEvent)

    fun launchUi(block: suspend () -> Unit): Job

    suspend fun handleError(baseError: BaseError) {
        when (baseError.importanceError) {
            CRITICAL_ERROR -> onCriticalError(baseError)
            NON_CRITICAL_ERROR -> onNonCriticalError(baseError)
            CONTENT_ERROR -> onContentError(baseError)
        }
    }

    suspend fun onCriticalError(baseError: BaseError)

    suspend fun onNonCriticalError(baseError: BaseError)

    suspend fun onContentError(baseError: BaseError)

    data class ShowEvent(val showAction: ShowAction)
}
