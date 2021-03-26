package com.zakrodionov.practicalapp.app.ui

import androidx.annotation.LayoutRes
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseFragment
import com.zakrodionov.practicalapp.app.core.BaseViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container

abstract class StubViewModel : BaseViewModel<Unit, Unit>() {
    override val container: Container<Unit, Unit> = container(Unit)
}

abstract class StubFragment(@LayoutRes layout: Int = R.layout.fragment_stub) : BaseFragment<Unit, Unit>(layout) {
    override fun sideEffect(event: Unit) = Unit
    override fun render(state: Unit) = Unit
}
