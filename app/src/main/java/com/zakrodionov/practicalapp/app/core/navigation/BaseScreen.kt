package com.zakrodionov.practicalapp.app.core.navigation

import androidx.annotation.CallSuper
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.androidx.AndroidScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.zakrodionov.common.extensions.OnLaunched
import com.zakrodionov.practicalapp.app.ui.theme.StatusBarColor

abstract class BaseScreen : AndroidScreen() {
    open fun statusBarColor(): Color = StatusBarColor
    open val useDarkIconsInStatusBar: Boolean = false

    @Composable
    @CallSuper
    override fun Content() {
        applyStatusBarColor()
    }

    @Composable
    private fun applyStatusBarColor(): Boolean {
        val systemUiController = rememberSystemUiController()
        OnLaunched { systemUiController.setStatusBarColor(statusBarColor(), useDarkIconsInStatusBar) }
        return true
    }
}
