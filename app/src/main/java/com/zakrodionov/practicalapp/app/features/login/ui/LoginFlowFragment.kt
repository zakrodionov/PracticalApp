package com.zakrodionov.practicalapp.app.features.login.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.github.terrakok.cicerone.Screen
import com.zakrodionov.common.extensions.setSoftInputModeAdjustPan
import com.zakrodionov.common.extensions.setSoftInputModeAlwaysVisible
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseFlowFragment
import com.zakrodionov.practicalapp.app.features.login.LoginScreens.PhoneScreen
import com.zakrodionov.practicalapp.app.features.login.di.LOGIN_QUALIFIER
import com.zakrodionov.practicalapp.app.features.login.ui.password.PasswordScreen

class LoginFlowFragment : BaseFlowFragment(
    R.layout.layout_fragment_container,
    R.id.fragmentContainerView,
    LOGIN_QUALIFIER
) {

    companion object {
        fun newInstance() = LoginFlowFragment()
    }

    override val startScreen: Screen = PhoneScreen()

    override fun onResume() {
        super.onResume()
        requireActivity().window?.setSoftInputModeAlwaysVisible()
    }

    override fun onStop() {
        super.onStop()
        requireActivity().window?.setSoftInputModeAdjustPan()
    }
}

enum class LoginScreens(val route: String) {
    LOGIN("login"),
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

@Composable
fun LoginFlow() {

}