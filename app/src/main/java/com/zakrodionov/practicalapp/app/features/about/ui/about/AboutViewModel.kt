package com.zakrodionov.practicalapp.app.features.about.ui.about

import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.lifecycle.SavedStateHandle
import com.zakrodionov.practicalapp.app.core.BaseError
import com.zakrodionov.practicalapp.app.core.BaseShowEvent.ShowDialog
import com.zakrodionov.practicalapp.app.core.BaseShowEvent.ShowSnackbar
import com.zakrodionov.practicalapp.app.core.BaseViewModel
import com.zakrodionov.practicalapp.app.core.ImportanceError.CONTENT_ERROR
import com.zakrodionov.practicalapp.app.core.ImportanceError.CRITICAL_ERROR
import com.zakrodionov.practicalapp.app.core.ImportanceError.NON_CRITICAL_ERROR
import com.zakrodionov.practicalapp.app.core.navigation.FlowRouter
import com.zakrodionov.practicalapp.app.environment.preferences.ApplicationSettings
import com.zakrodionov.practicalapp.app.environment.preferences.ApplicationSettings.Companion.KEY_IS_LOGGED
import com.zakrodionov.practicalapp.app.features.login.LoginScreens.LoginFlowScreen

class AboutViewModel(
    savedStateHandle: SavedStateHandle,
    private val applicationSettings: ApplicationSettings,
    private val flowRouter: FlowRouter
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
        flowRouter.newRootFlow(LoginFlowScreen())
    }

    private fun navigateToLoginFlow() {
        flowRouter.externalNavigateTo(LoginFlowScreen())
    }

    override suspend fun handleError(baseError: BaseError) {
        when (baseError.importanceError) {
            CRITICAL_ERROR -> postShowEvent(ShowDialog(baseError.title, baseError.message))
            NON_CRITICAL_ERROR -> postShowEvent(ShowSnackbar(baseError.message))
            CONTENT_ERROR -> reduce { state.copy(error = baseError) }
        }
    }
}
