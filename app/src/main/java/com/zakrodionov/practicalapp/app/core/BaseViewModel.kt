package com.zakrodionov.practicalapp.app.core

import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zakrodionov.practicalapp.app.core.dispatchers.DispatchersProvider
import com.zakrodionov.practicalapp.app.core.dispatchers.DispatchersProviderImpl
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Если нужно чтоб state переживал "убийство" процесса приложения
// прокидываем savedStateHandle для сохранения стейта в bundle
@Suppress("TooManyFunctions")
abstract class BaseViewModel<STATE : Parcelable, EVENT : Any>(
    initialState: STATE,
    protected open val savedStateHandle: SavedStateHandle? = null,
    protected open val dispatchersProvider: DispatchersProvider = DispatchersProviderImpl
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
    private val _showEventFlow = MutableSharedFlow<BaseShowEvent>()
    val showEventFlow = _showEventFlow.asSharedFlow()

    val state: STATE get() = stateFlow.value

    init {
        setSavedStateProvider()
    }

    fun update(function: (prevState: STATE) -> STATE) {
        _stateFlow.update(function)
    }

    protected suspend fun postEvent(event: EVENT) {
        _eventFlow.emit(event)
    }

    protected suspend fun postShowEvent(showEvent: BaseShowEvent) {
        _showEventFlow.emit(showEvent)
    }

    protected fun launch(block: suspend () -> Unit): Job = viewModelScope.launch(dispatchersProvider.main) {
        block.invoke()
    }

    protected fun launchIo(block: suspend () -> Unit): Job = viewModelScope.launch(dispatchersProvider.io) {
        block.invoke()
    }

    private fun restoreState(): STATE? {
        val bundle = savedStateHandle?.get<Bundle>(BUNDLE_STATE)
        return bundle?.getParcelable(KEY_STATE)
    }

    private fun setSavedStateProvider() {
        savedStateHandle?.setSavedStateProvider(BUNDLE_STATE) {
            bundleOf(KEY_STATE to state)
        }
    }

    // Показываем диалог
    protected suspend fun handleImportantError(baseError: BaseError) =
        postShowEvent(ShowDialog(baseError.title, baseError.message))

    // Показываем снекбар
    protected suspend fun handleNotImportantError(baseError: BaseError) =
        postShowEvent(ShowSnackbar(baseError.message))
}
