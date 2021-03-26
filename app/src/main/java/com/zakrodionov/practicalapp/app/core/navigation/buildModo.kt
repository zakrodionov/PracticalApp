package com.zakrodionov.practicalapp.app.core.navigation

import android.content.Context
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.MultiReducer
import com.github.terrakok.modo.android.AppReducer
import com.github.terrakok.modo.android.LogReducer
import com.zakrodionov.common.extensions.isDebug

fun buildModo(context: Context): Modo {
    val navigationReducer = AppReducer(context, MultiReducer())
    return Modo(if (isDebug) LogReducer(navigationReducer) else navigationReducer)
}