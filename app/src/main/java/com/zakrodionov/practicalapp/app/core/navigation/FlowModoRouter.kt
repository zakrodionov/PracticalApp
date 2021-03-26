package com.zakrodionov.practicalapp.app.core.navigation

import android.os.Bundle
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.NavigationRender
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.android.AppScreen
import com.github.terrakok.modo.android.init
import com.github.terrakok.modo.android.saveState
import com.github.terrakok.modo.back
import com.github.terrakok.modo.backTo
import com.github.terrakok.modo.forward
import com.github.terrakok.modo.replace

// Todo Ждем когда уберут final с Modo для наследования
class FlowModoRouter(val appModo: Modo, val flowModo: Modo) {

    fun replace(screen: AppScreen) = flowModo.replace(screen)

    fun forward(screen: AppScreen) = flowModo.forward(screen)

    fun back() = flowModo.back()

    fun backTo(screenId: String) = flowModo.backTo(screenId)

    fun startFlow(screen: Screen) = appModo.forward(screen)

    fun finishFlow() = appModo.back()

    fun init(bundle: Bundle?, firstScreen: Screen) = flowModo.init(bundle, firstScreen)

    fun saveState(bundle: Bundle) = flowModo.saveState(bundle)

    fun render(render: NavigationRender?) {
        flowModo.render = render
    }
}
