package com.zakrodionov.practicalapp.app.di.initializer

import com.github.terrakok.cicerone.Cicerone
import com.zakrodionov.practicalapp.app.core.navigation.GlobalRouter
import com.zakrodionov.practicalapp.app.di.DIQualifiers
import org.koin.core.module.Module

const val GLOBAL_QUALIFIER = "GLOBAL_QUALIFIER"

object GlobalNavigationInitializer : Initializer {

    override fun initialize(appModule: Module) {
        appModule.apply {
            val ciceroneQualifier = DIQualifiers.ciceroneQualifier(GLOBAL_QUALIFIER)
            val navigationHolderQualifier = DIQualifiers.navigationHolderQualifier(GLOBAL_QUALIFIER)

            // region Global navigation
            single(ciceroneQualifier) {
                Cicerone.create(GlobalRouter())
            }

            single(navigationHolderQualifier) {
                get<Cicerone<GlobalRouter>>(ciceroneQualifier).getNavigatorHolder()
            }

            single {
                get<Cicerone<GlobalRouter>>(ciceroneQualifier).router
            }
            //endregion
        }
    }
}
