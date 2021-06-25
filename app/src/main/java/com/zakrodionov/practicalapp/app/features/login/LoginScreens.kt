package com.zakrodionov.practicalapp.app.features.login

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.zakrodionov.practicalapp.app.features.login.ui.LoginFlowFragment
import com.zakrodionov.practicalapp.app.features.login.ui.password.PasswordFragment
import com.zakrodionov.practicalapp.app.features.login.ui.phone.PhoneFragment

@Suppress("FunctionName")
object LoginScreens {
    fun LoginFlowScreen() = FragmentScreen("LoginFlowScreen") {
        LoginFlowFragment.newInstance()
    }

    fun PhoneScreen() = FragmentScreen("PhoneScreen") {
        PhoneFragment()
    }

    fun PasswordScreen() = FragmentScreen("PasswordScreen") {
        PasswordFragment()
    }
}
