package com.zakrodionov.practicalapp.app.ui

import com.zakrodionov.practicalapp.app.core.BaseError
import com.zakrodionov.practicalapp.app.core.BaseState
import com.zakrodionov.practicalapp.app.core.BaseViewModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class StubState(
    override val error: BaseError? = null,
    override val isLoading: Boolean = false
) : BaseState {
    override fun applyError(error: BaseError): StubState = copy(error = error)
}

abstract class StubViewModel : BaseViewModel<StubState, Unit>() {
    override fun getInitialState() = StubState()
}
