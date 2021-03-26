package com.zakrodionov.practicalapp.app.ui.login

import com.github.terrakok.modo.android.AppScreen
import com.zakrodionov.practicalapp.app.core.BaseFlowFragment
import com.zakrodionov.practicalapp.app.core.navigation.FlowModoRouter
import com.zakrodionov.practicalapp.app.ui.Screens
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.fragmentScope
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.core.scope.Scope

class LoginFlowFragment : BaseFlowFragment() {

    override val loadModule = loadKoinModules(loginFlowModule)
    override val scope: Scope by fragmentScope()
    override val flowModoRouter: FlowModoRouter by inject()

    override fun getFirstScreen(): AppScreen = Screens.EmailScreen()
}
