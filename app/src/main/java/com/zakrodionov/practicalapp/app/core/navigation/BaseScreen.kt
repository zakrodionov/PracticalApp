package com.zakrodionov.practicalapp.app.core.navigation

import androidx.annotation.CallSuper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import cafe.adriel.voyager.androidx.AndroidScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.zakrodionov.practicalapp.app.ui.theme.ColorStatusBar

abstract class BaseScreen : AndroidScreen() {
    open val statusBarColor = ColorStatusBar
    open val isLight = false

    @Composable
    @CallSuper
    override fun Content() {
        applyStatusBarColor()
    }

    @Composable
    private fun applyStatusBarColor(): Boolean {
        val systemUiController = rememberSystemUiController()
        SideEffect { systemUiController.setStatusBarColor(statusBarColor, isLight) }
        return true
    }
}
