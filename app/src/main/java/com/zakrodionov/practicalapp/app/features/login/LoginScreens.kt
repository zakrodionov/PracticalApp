package com.zakrodionov.practicalapp.app.features.login

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.zakrodionov.practicalapp.app.features.login.ui.LoginFlowFragment
import com.zakrodionov.practicalapp.app.features.login.ui.password.PasswordFragment
import com.zakrodionov.practicalapp.app.features.login.ui.phone.PhoneFragment

@Suppress("FunctionName")
object LoginScreens {
    fun LoginFlowScreen(fromLaunchScreen: Boolean) = FragmentScreen("LoginFlowScreen") {
        LoginFlowFragment.newInstance(fromLaunchScreen)
    }

    fun PhoneScreen() = FragmentScreen("PhoneScreen") {
        PhoneFragment()
    }

    fun PasswordScreen() = FragmentScreen("PasswordScreen") {
        PasswordFragment()
    }
}
