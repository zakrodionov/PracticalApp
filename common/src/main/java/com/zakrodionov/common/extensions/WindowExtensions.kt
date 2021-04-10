package com.zakrodionov.common.extensions

import android.view.View
import android.view.Window
import androidx.core.view.WindowInsetsControllerCompat

fun Window.setStatusBarLightMode(
    isLightMode: Boolean,
    view: View = decorView
) {
    WindowInsetsControllerCompat(this, view).isAppearanceLightStatusBars = isLightMode
}
