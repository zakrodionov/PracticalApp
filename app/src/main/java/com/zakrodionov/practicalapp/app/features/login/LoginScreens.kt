package com.zakrodionov.practicalapp.app.features.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.zakrodionov.practicalapp.app.features.closeNestedNavGraph
import com.zakrodionov.practicalapp.app.features.login.password.PasswordScreen
import com.zakrodionov.practicalapp.app.features.login.phone.PhoneScreen

enum class LoginScreens(val route: String) {
    PHONE("login/phone"),
    PASSWORD("login/password")
}

fun NavGraphBuilder.addLoginGraph(
    navController: NavHostController,
) {
    composable(LoginScreens.PHONE.route) {
        PhoneScreen(navController)
    }
    composable(LoginScreens.PASSWORD.route) {
        PasswordScreen { navController.closeNestedNavGraph(this) }
    }
}
