package com.zakrodionov.practicalapp.app.di.modules

import com.zakrodionov.practicalapp.app.core.navigation.bindGlobalNavigation
import org.koin.dsl.module

const val GLOBAL_QUALIFIER = "GLOBAL_QUALIFIER"

val globalNavigationModule = module {
    bindGlobalNavigation(GLOBAL_QUALIFIER)
}
