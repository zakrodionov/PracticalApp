package com.zakrodionov.practicalapp.app.core.navigation

import android.os.Parcelable
import androidx.annotation.CallSuper
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.androidx.AndroidScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.zakrodionov.common.extensions.OnLaunched
import com.zakrodionov.practicalapp.app.core.ui.theme.StatusBarColor

abstract class BaseScreen : AndroidScreen(), Parcelable {
    open fun statusBarColor(): Color = StatusBarColor // Should be fun for non-Serializable data for state restoration
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
