package com.zakrodionov.practicalapp.app.core.navigation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen

// Для навигация во флоу
class FlowRouter(private val globalRouter: GlobalRouter) : Router() {

    fun externalNavigateTo(screen: Screen) = globalRouter.navigateTo(screen)

    fun newRootFlow(screen: Screen) = globalRouter.newRootScreen(screen)

    fun finishApp() {
        globalRouter.finishChain()
    }
}