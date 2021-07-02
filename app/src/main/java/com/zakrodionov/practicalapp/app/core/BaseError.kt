package com.zakrodionov.practicalapp.app.core

import android.os.Parcelable
import com.zakrodionov.common.core.TextResource
import com.zakrodionov.common.ui.lce.UiError
import com.zakrodionov.practicalapp.R
import kotlinx.parcelize.Parcelize

sealed class BaseError : Parcelable {
    open val title: TextResource = TextResource.fromStringId(R.string.error)
    open val message: TextResource = TextResource.fromStringId(R.string.unknown_error)
}

@Parcelize
object NetworkConnectionError : BaseError() {
    override val title: TextResource get() = TextResource.fromStringId(R.string.no_internet_connection_error_title)
    override val message: TextResource get() = TextResource.fromStringId(R.string.no_internet_connection_error_message)
}

@Parcelize
data class HttpError(
    val code: Int = 0,
    val status: TextResource = TextResource.empty,
) : BaseError()

@Parcelize
object UnknownError : BaseError()

fun BaseError.toUiError() = UiError(title, message, this is NetworkConnectionError)
