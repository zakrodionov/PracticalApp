package com.zakrodionov.practicalapp.app.core

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Если нужно чтоб state переживал "убийство" процесса приложения
// прокидываем savedStateHandle для сохранения стейта в bundle
@Suppress("TooManyFunctions")
abstract class BaseViewModel<STATE : Parcelable, EVENT : Any>(
    initialState: STATE,
    private val savedStateHandle: SavedStateHandle? = null
) : ViewModel() {

    companion object {
        const val BUNDLE_STATE = "BUNDLE_STATE"
        const val KEY_STATE = "KEY_STATE"
    }

    private val _stateFlow = MutableStateFlow<STATE>(restoreState() ?: initialState)
    val stateFlow = _stateFlow.asStateFlow()

    private val _eventFlow = MutableSharedFlow<EVENT>()
    val eventFlow = _eventFlow.asSharedFlow()

    // Общие ShowEvent события для toast, alert, dialog
    private val _showEventFlow = MutableSharedFlow<ShowEvent>()
    val showEventFlow = _showEventFlow.asSharedFlow()

    val state: STATE get() = stateFlow.value

    init {
        setSavedStateProvider()
    }

    protected suspend fun reduce(state: suspend () -> STATE) {
        _stateFlow.emit(state())
    }

    protected suspend fun postEvent(event: EVENT) {
        _eventFlow.emit(event)
    }

    protected suspend fun postShowEvent(showEvent: ShowEvent) {
        _showEventFlow.emit(showEvent)
    }

    protected fun launch(block: suspend () -> Unit): Job = viewModelScope.launch {
        block.invoke()
    }

    private fun restoreState(): STATE? {
        val bundle = savedStateHandle?.get<Bundle>(BUNDLE_STATE)
        return bundle?.getParcelable(KEY_STATE)
    }

    private fun setSavedStateProvider() {
        savedStateHandle?.setSavedStateProvider(BUNDLE_STATE) {
            Bundle().apply { putParcelable(KEY_STATE, state) }
        }
    }

    abstract suspend fun handleError(baseError: BaseError)

    data class ShowEvent(val showAction: ShowAction)
}
