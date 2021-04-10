package com.zakrodionov.practicalapp.app.ui.about

import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.lifecycle.SavedStateHandle
import com.github.terrakok.modo.Modo
import com.zakrodionov.practicalapp.app.core.BaseViewModel
import com.zakrodionov.practicalapp.app.core.navigation.launchFullScreenFlow
import com.zakrodionov.practicalapp.app.ui.login.loginFlow
import com.zakrodionov.practicalapp.data.local.ApplicationSettings
import com.zakrodionov.practicalapp.data.local.ApplicationSettings.Companion.KEY_IS_LOGGED

class AboutViewModel(
    savedStateHandle: SavedStateHandle,
    private val applicationSettings: ApplicationSettings,
    private val modo: Modo
) : BaseViewModel<AboutState, AboutEvent>(AboutState(), savedStateHandle) {

    init {
        getIsLogged()
    }

    private val settingsListener = OnSharedPreferenceChangeListener { _, key ->
        if (key == KEY_IS_LOGGED) {
            getIsLogged()
        }
    }

    init {
        subscribePreferences()
    }

    private fun getIsLogged() = launchUi {
        reduce { state.copy(isLogged = applicationSettings.isLogged) }
    }

    private fun subscribePreferences() {
        applicationSettings.registerListener(settingsListener)
    }

    override fun onCleared() {
        applicationSettings.unregisterListener(settingsListener)
        super.onCleared()
    }

    fun loginOrLogout() = launchUi {
        if (state.isLogged) logout() else navigateToLoginFlow()
    }

    private fun logout() {
        applicationSettings.isLogged = false
    }

    private fun navigateToLoginFlow() {
        modo.launchFullScreenFlow(loginFlow())
    }
}
