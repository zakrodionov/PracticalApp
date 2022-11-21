package com.zakrodionov.practicalapp.app.features.login.phone

import com.zakrodionov.practicalapp.app.core.BaseViewModel

class PhoneViewModel(
    // savedStateHandle: SavedStateHandle,
) : BaseViewModel<PhoneState, PhoneEvent>(PhoneState(), null) {

    fun setFormattedPhone(phone: String) {
        update { it.copy(formattedPhone = phone) }
    }
}
