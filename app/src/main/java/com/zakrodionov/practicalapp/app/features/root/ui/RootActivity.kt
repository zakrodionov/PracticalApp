package com.zakrodionov.practicalapp.app.features.root.ui

import android.content.Intent
import android.os.Bundle
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseActivity
import com.zakrodionov.practicalapp.app.core.navigation.BaseAnimatedNavigator
import com.zakrodionov.practicalapp.app.data.preferences.AppPreferences
import com.zakrodionov.practicalapp.app.features.bottom.BottomScreens.BottomTabsScreen
import com.zakrodionov.practicalapp.app.features.login.LoginScreens.LoginFlowScreen
import com.zakrodionov.practicalapp.app.features.notifications.FirebaseMsgService.Companion.ARG_PUSH_DATA
import org.koin.android.ext.android.inject

class RootActivity : BaseActivity() {

    private val navigatorHolder: NavigatorHolder by inject()
    private val navigator: AppNavigator =
        BaseAnimatedNavigator(this, supportFragmentManager, R.id.fragmentContainerView)
    private val appPreferences: AppPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_PracticalApp)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_fragment_container)

        handleIntent(intent, savedInstanceState)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent, null)
    }

    // TODO Перенести логику в VM
    private fun handleIntent(intent: Intent?, savedInstanceState: Bundle?) {
        val screen = intent?.extras?.getString(ARG_PUSH_DATA)
        val skipLoginScreen = appPreferences.isLogged || appPreferences.isSkipLoginFlow

        if (savedInstanceState == null) {
            when {
                screen == null && skipLoginScreen -> {
                    globalRouter.newRootScreen(BottomTabsScreen())
                }
                screen != null && skipLoginScreen -> {
                    globalRouter.newRootScreen(BottomTabsScreen(screen))
                }
                else -> {
                    globalRouter.newRootScreen(LoginFlowScreen(true))
                }
            }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}
