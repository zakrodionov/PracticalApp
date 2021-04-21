package com.zakrodionov.practicalapp.app.ui.about

import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.lifecycle.SavedStateHandle
import com.github.terrakok.modo.Modo
import com.zakrodionov.practicalapp.app.core.BaseError
import com.zakrodionov.practicalapp.app.core.BaseViewModel
import com.zakrodionov.practicalapp.app.core.ImportanceError.CONTENT_ERROR
import com.zakrodionov.practicalapp.app.core.ImportanceError.CRITICAL_ERROR
import com.zakrodionov.practicalapp.app.core.ImportanceError.NON_CRITICAL_ERROR
import com.zakrodionov.practicalapp.app.core.ShowAction.ShowDialog
import com.zakrodionov.practicalapp.app.core.ShowAction.ShowSnackbar
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

    private fun getIsLogged() = launch {
        reduce { state.copy(isLogged = applicationSettings.isLogged) }
    }

    private fun subscribePreferences() {
        applicationSettings.registerListener(settingsListener)
    }

    override fun onCleared() {
        applicationSettings.unregisterListener(settingsListener)
        super.onCleared()
    }

    fun loginOrLogout() = launch {
        if (state.isLogged) logout() else navigateToLoginFlow()
    }

    private fun logout() {
        applicationSettings.isLogged = false
    }

    private fun navigateToLoginFlow() {
        modo.launchFullScreenFlow(loginFlow())
    }

    override suspend fun handleError(baseError: BaseError) {
        when (baseError.importanceError) {
            CRITICAL_ERROR -> postShowEvent(ShowEvent(ShowDialog(baseError.title, baseError.message)))
            NON_CRITICAL_ERROR -> postShowEvent(ShowEvent(ShowSnackbar(baseError.message)))
            CONTENT_ERROR -> reduce { state.copy(error = baseError) }
        }
    }
}
