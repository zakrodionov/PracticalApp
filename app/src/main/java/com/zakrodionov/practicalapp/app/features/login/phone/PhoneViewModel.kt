package com.zakrodionov.practicalapp.app.features.login.phone

import androidx.lifecycle.SavedStateHandle
import com.zakrodionov.practicalapp.app.core.BaseViewModel

class PhoneViewModel(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<PhoneState, PhoneEvent>(PhoneState(), savedStateHandle) {

    fun setFormattedPhone(phone: String) {
        update { it.copy(formattedPhone = phone) }
    }
}
