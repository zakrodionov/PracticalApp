package com.zakrodionov.practicalapp.app.ui

import androidx.annotation.LayoutRes
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseError
import com.zakrodionov.practicalapp.app.core.BaseFragment
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

abstract class StubViewModel : BaseViewModel<StubState, Unit>(StubState())

abstract class StubFragment(@LayoutRes layout: Int = R.layout.fragment_stub) : BaseFragment<StubState, Unit>(layout) {
    override fun sideEffect(event: Unit) = Unit
    override fun render(state: StubState) = Unit
}
