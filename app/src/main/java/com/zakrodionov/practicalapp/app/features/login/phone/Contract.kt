package com.zakrodionov.practicalapp.app.features.login.phone

import android.os.Parcelable
import com.zakrodionov.common.extensions.extractDigits
import com.zakrodionov.common.extensions.isValidPhoneNumberLength
import com.zakrodionov.practicalapp.app.core.BaseError
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhoneState(
    val formattedPhone: String = "",
    val error: BaseError? = null,
    val isLoading: Boolean = false,
) : Parcelable {
    private val phone: String
        get() = formattedPhone.extractDigits()

    val phoneIsValid: Boolean
        get() = formattedPhone.isValidPhoneNumberLength()
}

sealed class PhoneEvent
