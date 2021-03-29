package com.zakrodionov.practicalapp.app.core.navigation

import android.content.Context
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.MultiReducer
import com.github.terrakok.modo.MultiScreen
import com.github.terrakok.modo.android.AppReducer
import com.github.terrakok.modo.android.LogReducer
import com.github.terrakok.modo.back
import com.github.terrakok.modo.backToTabRoot
import com.github.terrakok.modo.externalForward
import com.zakrodionov.common.extensions.isDebug

const val SINGLE_STACK_FLOW = "--SINGLE_STACK_FLOW--"

fun buildModo(context: Context): Modo {
    val navigationReducer = AppReducer(context, MultiReducer())
    return Modo(if (isDebug) LogReducer(navigationReducer) else navigationReducer)
}

fun Modo.finishFlow() {
    backToTabRoot()
    back()
}

fun Modo.launchFullScreenFlow(screen: MultiScreen) {
    externalForward(screen)
}

"fghfhfghgfh"