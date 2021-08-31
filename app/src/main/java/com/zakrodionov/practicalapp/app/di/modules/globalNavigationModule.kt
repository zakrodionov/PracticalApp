package com.zakrodionov.practicalapp.app.di.modules

import com.zakrodionov.practicalapp.app.core.navigation.bindGlobalNavigation
import org.koin.dsl.module

val globalNavigationModule = module {
    bindGlobalNavigation()
}
