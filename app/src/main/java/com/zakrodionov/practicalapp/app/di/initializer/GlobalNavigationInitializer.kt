package com.zakrodionov.practicalapp.app.di.initializer

import com.zakrodionov.practicalapp.app.core.navigation.bindGlobalNavigation
import org.koin.core.module.Module

const val GLOBAL_QUALIFIER = "GLOBAL_QUALIFIER"

object GlobalNavigationInitializer : Initializer {

    override fun initialize(appModule: Module) {
        appModule.apply {
            bindGlobalNavigation(GLOBAL_QUALIFIER)
        }
    }
}
