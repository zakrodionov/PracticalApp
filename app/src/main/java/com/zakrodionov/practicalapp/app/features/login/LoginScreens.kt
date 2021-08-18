package com.zakrodionov.practicalapp.app.features.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zakrodionov.practicalapp.app.features.login.ui.password.PasswordScreen
import com.zakrodionov.practicalapp.app.features.login.ui.phone.PhoneScreen

enum class LoginScreens(val route: String) {
    PHONE("login/phone"),
    PASSWORD("login/password")
}

fun NavGraphBuilder.addLoginGraph(
    navigateToPassword: () -> Unit,
    popToRoot: () -> Unit,
) {
    composable(LoginScreens.PHONE.route) {
        PhoneScreen(navigateToPassword)
    }
    composable(LoginScreens.PASSWORD.route) {
        PasswordScreen(popToRoot)
    }
}
