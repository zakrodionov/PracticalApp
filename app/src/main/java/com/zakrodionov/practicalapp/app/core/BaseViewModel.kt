package com.zakrodionov.practicalapp.app.core

import androidx.lifecycle.ViewModel
import com.zakrodionov.practicalapp.app.core.ImportanceError.CONTENT_ERROR
import com.zakrodionov.practicalapp.app.core.ImportanceError.CRITICAL_ERROR
import com.zakrodionov.practicalapp.app.core.ImportanceError.NON_CRITICAL_ERROR
import org.orbitmvi.orbit.ContainerHost

@Suppress("UNCHECKED_CAST")
abstract class BaseViewModel<STATE : Any, SIDE_EFFECT : Any> : ViewModel(), ContainerHost<STATE, SIDE_EFFECT> {

    open fun handleError(baseError: BaseError) {
        when (baseError.importanceError) {
            CRITICAL_ERROR -> onCriticalError(baseError)
            NON_CRITICAL_ERROR -> onNonCriticalError(baseError)
            CONTENT_ERROR -> onContentError(baseError)
        }
    }

    open fun onCriticalError(baseError: BaseError) = Unit
    open fun onNonCriticalError(baseError: BaseError) = Unit
    open fun onContentError(baseError: BaseError) = Unit
}
//"sdfsdf"