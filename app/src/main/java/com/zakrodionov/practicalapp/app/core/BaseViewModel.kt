package com.zakrodionov.practicalapp.app.core

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zakrodionov.common.ui.ShowAction.ShowDialog
import com.zakrodionov.common.ui.ShowAction.ShowSnackbar
import com.zakrodionov.practicalapp.app.core.IBaseViewModel.ShowEvent
import com.zakrodionov.practicalapp.app.core.ImportanceError.CONTENT_ERROR
import com.zakrodionov.practicalapp.app.core.ImportanceError.CRITICAL_ERROR
import com.zakrodionov.practicalapp.app.core.ImportanceError.NON_CRITICAL_ERROR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// State переживает "убийство" процесса приложения с помощью savedStateHandle
@Suppress("TooManyFunctions")
abstract class BaseViewModel<STATE : BaseState, EVENT : Any>(
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), IBaseViewModel<STATE, EVENT> {

    companion object {
        const val BUNDLE_STATE = "BUNDLE_STATE"
        const val KEY_STATE = "KEY_STATE"
    }

    private val _stateFlow = MutableStateFlow<STATE>(restoreState())
    override val stateFlow = _stateFlow.asStateFlow()

    private val _eventFlow = MutableSharedFlow<EVENT>()
    override val eventFlow = _eventFlow.asSharedFlow()

    // Общие ShowEvent события для toast, alert, dialog
    private val _showEventFlow = MutableSharedFlow<ShowEvent>()
    override val showEventFlow = _showEventFlow.asSharedFlow()

    init {
        setSavedStateProvider()
    }

    override suspend fun reduce(state: suspend () -> STATE) {
        _stateFlow.emit(state())
    }

    override suspend fun postEvent(event: EVENT) {
        _eventFlow.emit(event)
    }

    override suspend fun postShowEvent(showEvent: ShowEvent) {
        _showEventFlow.emit(showEvent)
    }

    override fun launchUi(block: suspend () -> Unit) = viewModelScope.launch(Dispatchers.Main) {
        block.invoke()
    }

    private fun restoreState(): STATE {
        val bundle = savedStateHandle.get<Bundle>(BUNDLE_STATE)
        val restoredState = bundle?.getParcelable<STATE>(KEY_STATE)
        return restoredState ?: getInitialState()
    }

    private fun setSavedStateProvider() {
        savedStateHandle.setSavedStateProvider(BUNDLE_STATE) {
            Bundle().apply { putParcelable(KEY_STATE, state) }
        }
    }

    override suspend fun handleError(baseError: BaseError) {
        when (baseError.importanceError) {
            CRITICAL_ERROR -> onCriticalError(baseError)
            NON_CRITICAL_ERROR -> onNonCriticalError(baseError)
            CONTENT_ERROR -> onContentError(baseError)
        }
    }

    override suspend fun onCriticalError(baseError: BaseError) {
        postShowEvent(ShowEvent(ShowDialog(baseError.title, baseError.message)))
    }

    override suspend fun onNonCriticalError(baseError: BaseError) {
        postShowEvent(ShowEvent(ShowSnackbar(baseError.message)))
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun onContentError(baseError: BaseError) {
        reduce { state.applyError(error = baseError) as STATE }
    }
}
