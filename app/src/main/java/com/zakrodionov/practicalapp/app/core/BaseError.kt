package com.zakrodionov.practicalapp.app.core

import android.os.Parcelable
import com.zakrodionov.common.ui.models.ResourceString
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.ImportanceError.CONTENT_ERROR
import com.zakrodionov.practicalapp.app.core.ImportanceError.CRITICAL_ERROR
import com.zakrodionov.practicalapp.app.core.ImportanceError.NON_CRITICAL_ERROR
import kotlinx.parcelize.Parcelize

sealed class BaseError(open val importanceError: ImportanceError = NON_CRITICAL_ERROR) : Parcelable {
    open val title: ResourceString = ResourceString.Res(R.string.error)
    open val message: ResourceString = ResourceString.Res(R.string.unknown_error)
}

// Ошибка может отображаться и как состояние, и как событие
enum class ImportanceError {
    // Критичная ошибка, реакция по умолчанию показ модального диалога
    CRITICAL_ERROR,

    // Некритичная ошибка, реакция по умолчанию показ тоаста/снекбара
    NON_CRITICAL_ERROR,

    // Контентная ошибка, реакция по умолчанию скрытие UI экрана, отображение специального вью для ошибки
    CONTENT_ERROR
}

@Parcelize
data class NetworkConnectionError(override val importanceError: ImportanceError = CONTENT_ERROR) : BaseError() {
    override val message: ResourceString get() = ResourceString.Res(R.string.no_internet_connection_error)
}

@Parcelize
data class HttpError(
    val code: Int = 0,
    val status: ResourceString = ResourceString.empty,
    override val importanceError: ImportanceError = CRITICAL_ERROR
) : BaseError()

@Parcelize
data class UnknownError(override val importanceError: ImportanceError = NON_CRITICAL_ERROR) : BaseError()
