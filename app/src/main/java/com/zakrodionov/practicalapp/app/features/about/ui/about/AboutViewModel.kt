package com.zakrodionov.practicalapp.app.features.about.ui.about

import androidx.lifecycle.SavedStateHandle
import com.zakrodionov.common.extensions.preferenceListener
import com.zakrodionov.practicalapp.app.core.BaseViewModel
import com.zakrodionov.practicalapp.app.core.navigation.FlowRouter
import com.zakrodionov.practicalapp.app.data.preferences.AppPreferences
import com.zakrodionov.practicalapp.app.data.preferences.AppPreferences.Companion.KEY_IS_LOGGED
import com.zakrodionov.practicalapp.app.features.login.LoginScreens.LoginFlowScreen

class AboutViewModel(
    savedStateHandle: SavedStateHandle,
    private val appPreferences: AppPreferences,
    private val flowRouter: FlowRouter
) : BaseViewModel<AboutState, AboutEvent>(AboutState(), savedStateHandle) {

    init {
        getIsLogged()
    }

    private val settingsListener = preferenceListener(KEY_IS_LOGGED) {
        getIsLogged()
    }

    init {
        subscribePreferences()
    }

    private fun getIsLogged() {
        reduce { state.copy(isLogged = appPreferences.isLogged) }
    }

    private fun subscribePreferences() {
        appPreferences.registerListener(settingsListener)
    }

    override fun onCleared() {
        appPreferences.unregisterListener(settingsListener)
        super.onCleared()
    }

    fun loginOrLogout() {
        if (state.isLogged) logout() else navigateToLoginFlow()
    }

    private fun logout() {
        appPreferences.isLogged = false
    }

    private fun navigateToLoginFlow() {
        flowRouter.externalNavigateTo(LoginFlowScreen())
    }
}
