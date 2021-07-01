package com.zakrodionov.common.extensions

import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

fun Window.setStatusBarLightMode(
    isLightMode: Boolean,
    view: View = decorView
) {
    WindowInsetsControllerCompat(this, view).isAppearanceLightStatusBars = isLightMode
}

fun Window.setSoftInputModeAlwaysVisible() {
    setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
}

fun Window.setSoftInputModeAdjustPan() {
    setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
}

fun Window.disableFitsSystemWindows() = WindowCompat.setDecorFitsSystemWindows(this, false)
