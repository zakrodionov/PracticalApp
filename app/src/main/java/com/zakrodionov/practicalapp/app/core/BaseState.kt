package com.zakrodionov.practicalapp.app.core

import android.os.Parcelable

interface BaseState<T : Parcelable> : Parcelable {
    val error: BaseError?
    val isLoading: Boolean

    fun applyError(error: BaseError): T
}
