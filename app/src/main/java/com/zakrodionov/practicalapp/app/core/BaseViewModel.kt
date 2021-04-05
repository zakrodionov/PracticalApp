package com.zakrodionov.practicalapp.app.core

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zakrodionov.common.ui.ShowAction
import com.zakrodionov.practicalapp.app.core.ImportanceError.CONTENT_ERROR
import com.zakrodionov.practicalapp.app.core.ImportanceError.CRITICAL_ERROR
import com.zakrodionov.practicalapp.app.core.ImportanceError.NON_CRITICAL_ERROR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

// Если нужно чтоб state переживал "убийство" процесса приложения
// прокидываем savedStateHandle для сохранения стейта в bundle
@Suppress("TooManyFunctions")
abstract class BaseViewModel<STATE : BaseState<STATE>, EVENT : Any>(
    private val savedStateHandle: SavedStateHandle? = null
) : ViewModel() {

    companion object {
        const val BUNDLE_STATE = "BUNDLE_STATE"
        const val KEY_STATE = "KEY_STATE"
    }

    val stateFlow = MutableStateFlow<STATE>(restoreState())
    val eventFlow = MutableSharedFlow<EVENT>()

    // Общие ShowEvent события для toast, alert, dialog
    val showEventFlow = MutableSharedFlow<ShowEvent>()

    abstract fun getInitialState(): STATE

    val state: STATE get() = stateFlow.value

    init {
        setSavedStateProvider()
    }

    suspend fun reduce(state: () -> STATE) {
        stateFlow.emit(state())
    }

    suspend fun postEvent(event: EVENT) {
        eventFlow.emit(event)
    }

    suspend fun postShowEvent(showEvent: ShowEvent) {
        showEventFlow.emit(showEvent)
    }

    fun launchUi(block: suspend () -> Unit) = viewModelScope.launch(Dispatchers.Main) {
        block.invoke()
    }

    private fun restoreState(): STATE {
        val bundle = savedStateHandle?.get<Bundle>(BUNDLE_STATE)
        val restoredState = bundle?.getParcelable<STATE>(KEY_STATE)
        return restoredState ?: getInitialState()
    }

    private fun setSavedStateProvider() {
        savedStateHandle?.setSavedStateProvider(BUNDLE_STATE) {
            Bundle().apply { putParcelable(KEY_STATE, state) }
        }
    }

    open suspend fun handleError(baseError: BaseError) {
        when (baseError.importanceError) {
            CRITICAL_ERROR -> onCriticalError(baseError)
            NON_CRITICAL_ERROR -> onNonCriticalError(baseError)
            CONTENT_ERROR -> onContentError(baseError)
        }
    }

    open suspend fun onCriticalError(baseError: BaseError) {
        postShowEvent(ShowEvent(ShowAction.ShowDialog(baseError.title, baseError.message)))
    }

    open suspend fun onNonCriticalError(baseError: BaseError) {
        postShowEvent(ShowEvent(ShowAction.ShowSnackbar(baseError.message)))
    }

    open suspend fun onContentError(baseError: BaseError) {
        reduce { state.applyError(error = baseError) }
    }

    data class ShowEvent(val showAction: ShowAction)
}
