package com.zakrodionov.practicalapp.app.features.login

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.zakrodionov.practicalapp.app.features.login.ui.LoginFlowFragment
import com.zakrodionov.practicalapp.app.features.login.ui.email.EmailFragment
import com.zakrodionov.practicalapp.app.features.login.ui.password.PasswordFragment

@Suppress("FunctionName")
object LoginScreens {
    fun LoginFlowScreen() = FragmentScreen("LoginFlowScreen") {
        LoginFlowFragment.newInstance()
    }

    fun EmailScreen() = FragmentScreen("EmailScreen") {
        EmailFragment()
    }

    fun PasswordScreen() = FragmentScreen("FragmentScreen") {
        PasswordFragment()
    }
}
