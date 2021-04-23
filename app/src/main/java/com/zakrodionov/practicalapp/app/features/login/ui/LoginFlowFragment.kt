package com.zakrodionov.practicalapp.app.features.login.ui

import com.github.terrakok.cicerone.Screen
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseTabFragment
import com.zakrodionov.practicalapp.app.features.login.LoginScreens.EmailScreen
import com.zakrodionov.practicalapp.app.features.login.di.LOGIN_QUALIFIER

class LoginFlowFragment : BaseTabFragment(
    R.layout.layout_fragment_container,
    R.id.fragmentContainerView,
    LOGIN_QUALIFIER
) {

    companion object {
        fun newInstance() = LoginFlowFragment()
    }

    override val startScreen: Screen = EmailScreen()
}
