package com.zakrodionov.practicalapp.app.ui.login

import com.github.terrakok.modo.android.AppScreen
import com.github.terrakok.modo.android.MultiAppScreen
import com.zakrodionov.practicalapp.app.core.navigation.SINGLE_STACK_FLOW
import com.zakrodionov.practicalapp.app.ui.login.email.EmailFragment
import com.zakrodionov.practicalapp.app.ui.login.password.PasswordFragment
import kotlinx.parcelize.Parcelize

fun loginFlow(firstScreen: AppScreen = EmailScreen()) =
    MultiAppScreen("LoginFlow-$SINGLE_STACK_FLOW", listOf(firstScreen), 0)

@Parcelize
class EmailScreen : AppScreen("EmailScreen") {
    override fun create() = EmailFragment()
}

@Parcelize
class PasswordScreen : AppScreen("PasswordScreen") {
    override fun create() = PasswordFragment()
}
