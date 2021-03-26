package com.zakrodionov.practicalapp.app.ui.about

import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.lifecycle.SavedStateHandle
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.externalForward
import com.zakrodionov.practicalapp.app.core.BaseViewModel
import com.zakrodionov.practicalapp.app.ui.Screens
import com.zakrodionov.practicalapp.data.local.ApplicationSettings
import com.zakrodionov.practicalapp.data.local.ApplicationSettings.Companion.KEY_IS_LOGGED
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class AboutViewModel(
    savedStateHandle: SavedStateHandle,
    private val applicationSettings: ApplicationSettings,
    private val modo: Modo
) : BaseViewModel<AboutState, AboutEvent>() {

    override val container: Container<AboutState, AboutEvent> = container(AboutState(), savedStateHandle) {
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

    private fun getIsLogged() = intent {
        reduce { state.copy(isLogged = applicationSettings.isLogged) }
    }

    private fun subscribePreferences() {
        applicationSettings.registerListener(settingsListener)
    }

    override fun onCleared() {
        applicationSettings.unregisterListener(settingsListener)
        super.onCleared()
    }

    fun loginOrLogout() = intent {
        withContext(Dispatchers.Main) { if (state.isLogged) logout() else navigateToLoginFlow() }
    }

    private fun logout() {
        applicationSettings.isLogged = false
    }

    private fun navigateToLoginFlow() {
        modo.externalForward(Screens.LoginFlowScreen())
    }
}
