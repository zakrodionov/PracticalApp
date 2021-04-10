package com.zakrodionov.practicalapp.app.ui

import androidx.annotation.LayoutRes
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseError
import com.zakrodionov.practicalapp.app.core.BaseFragment
import com.zakrodionov.practicalapp.app.core.BaseUnsavedViewModel

abstract class StubViewModel : BaseUnsavedViewModel<Unit, Unit>() {
    override fun getInitialState() = Unit
    override suspend fun onContentError(baseError: BaseError) = Unit
}

abstract class StubFragment(@LayoutRes layout: Int = R.layout.fragment_stub) : BaseFragment<Unit, Unit>(layout) {
    override fun sideEffect(event: Unit) = Unit
    override fun render(state: Unit) = Unit
}
