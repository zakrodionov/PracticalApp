package com.zakrodionov.practicalapp.app.core

import android.os.Parcelable

interface BaseState : Parcelable {
    val error: BaseError?
    val isLoading: Boolean

    fun applyError(error: BaseError): BaseState
}
