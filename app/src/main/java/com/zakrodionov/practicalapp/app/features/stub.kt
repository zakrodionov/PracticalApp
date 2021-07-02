package com.zakrodionov.practicalapp.app.features

import android.os.Parcelable
import androidx.annotation.LayoutRes
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseError
import com.zakrodionov.practicalapp.app.core.BaseFragment
import com.zakrodionov.practicalapp.app.core.BaseViewModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class StubState(
    val error: BaseError? = null,
    val isLoading: Boolean = false
) : Parcelable

abstract class StubViewModel : BaseViewModel<StubState, Unit>(StubState())

abstract class StubFragment(@LayoutRes layout: Int = R.layout.fragment_stub) :
    BaseFragment<StubState, Unit>(layout) {
    override fun render(state: StubState) = Unit
    override fun sideEffect(event: Unit) = Unit
}
