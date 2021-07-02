package com.zakrodionov.practicalapp.app.features.login.ui.phone

import androidx.lifecycle.SavedStateHandle
import com.zakrodionov.common.core.TextResource
import com.zakrodionov.practicalapp.app.core.BaseViewModel
import com.zakrodionov.practicalapp.app.core.ShowToast
import com.zakrodionov.practicalapp.app.core.navigation.FlowRouter
import com.zakrodionov.practicalapp.app.features.login.LoginScreens.PasswordScreen

class PhoneViewModel(
    savedStateHandle: SavedStateHandle,
    private val flowRouter: FlowRouter,
) : BaseViewModel<PhoneState, PhoneEvent>(PhoneState(), savedStateHandle) {

    fun nextScreen() {
        launch {
            postShowEvent(ShowToast(TextResource.fromText("Phone is: ${state.formattedPhone}")))
        }
        flowRouter.navigateTo(PasswordScreen())
    }

    fun setFormattedPhone(phone: String) {
        reduce { state.copy(formattedPhone = phone) }
    }
}
