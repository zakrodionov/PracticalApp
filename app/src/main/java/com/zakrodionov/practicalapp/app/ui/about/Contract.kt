package com.zakrodionov.practicalapp.app.ui.about

import com.zakrodionov.practicalapp.app.core.BaseError
import com.zakrodionov.practicalapp.app.core.BaseState
import kotlinx.parcelize.Parcelize

@Parcelize
data class AboutState(
    val isLogged: Boolean = false,
    override val error: BaseError? = null,
    override val isLoading: Boolean = false
) : BaseState {
    override fun applyError(error: BaseError): AboutState = copy(error = error)
}

sealed class AboutEvent
