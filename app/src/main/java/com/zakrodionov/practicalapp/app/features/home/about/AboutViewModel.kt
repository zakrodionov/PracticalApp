package com.zakrodionov.practicalapp.app.features.home.about

import com.zakrodionov.common.extensions.preferenceListener
import com.zakrodionov.practicalapp.app.core.BaseViewModel
import com.zakrodionov.practicalapp.app.data.preferences.AppPreferences
import com.zakrodionov.practicalapp.app.data.preferences.AppPreferences.Companion.KEY_IS_LOGGED

class AboutViewModel(
    // savedStateHandle: SavedStateHandle,
    private val appPreferences: AppPreferences,
) : BaseViewModel<AboutState, AboutEvent>(AboutState(), null) {

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
        launch {
            postEvent(NavigateToLoginFlow)
        }
    }
}
