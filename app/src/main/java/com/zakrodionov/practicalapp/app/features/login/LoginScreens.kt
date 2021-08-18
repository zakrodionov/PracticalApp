package com.zakrodionov.practicalapp.app.features.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zakrodionov.practicalapp.app.features.login.ui.password.PasswordScreen

enum class LoginScreens(val route: String) {
    PHONE("login/phone"),
    PASSWORD("login/password")
}

fun NavGraphBuilder.addLoginGraph(
    navigateToPassword: () -> Unit,
    popToRoot: () -> Unit,
) {
    composable(LoginScreens.PHONE.route) {
        com.zakrodionov.practicalapp.app.features.login.ui.phone.PhoneScreen(navigateToPassword)
    }
    composable(LoginScreens.PASSWORD.route) {
        PasswordScreen(popToRoot)
    }
}
