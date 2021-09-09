package com.zakrodionov.practicalapp.app.features.root.ui

import android.os.Bundle
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseActivity
import com.zakrodionov.practicalapp.app.core.navigation.BaseNavigator
import com.zakrodionov.practicalapp.app.core.navigation.GlobalRouter
import com.zakrodionov.practicalapp.app.features.bottom.BottomScreens.BottomTabsScreen
import org.koin.android.ext.android.inject

class RootActivity : BaseActivity() {

    private val globalRouter: GlobalRouter by inject()
    private val navigatorHolder: NavigatorHolder by inject()
    private val navigator: AppNavigator =
        BaseNavigator(this, supportFragmentManager, R.id.fragmentContainerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_PracticalApp)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_fragment_container)

        if (savedInstanceState == null) {
            globalRouter.newRootScreen(BottomTabsScreen())
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
