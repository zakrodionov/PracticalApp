package com.zakrodionov.practicalapp.app.features.about.di

import com.github.terrakok.cicerone.Cicerone
import com.zakrodionov.practicalapp.app.core.navigation.FlowRouter
import com.zakrodionov.practicalapp.app.di.DIQualifiers
import com.zakrodionov.practicalapp.app.features.about.ui.about.AboutViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

const val ABOUT_QUALIFIER = "ABOUT_QUALIFIER"

val aboutModule = module {
    val ciceroneQualifier = DIQualifiers.ciceroneQualifier(ABOUT_QUALIFIER)
    val navigationHolderQualifier = DIQualifiers.navigationHolderQualifier(ABOUT_QUALIFIER)
    val routerQualifier = DIQualifiers.routerQualifier(ABOUT_QUALIFIER)

    viewModel { AboutViewModel(get(), get(), get(routerQualifier)) }

    // region Flow navigation
    single(ciceroneQualifier) {
        Cicerone.create(FlowRouter(get(), routerQualifier.value))
    }

    single(navigationHolderQualifier) {
        get<Cicerone<FlowRouter>>(ciceroneQualifier).getNavigatorHolder()
    }

    single(routerQualifier) {
        get<Cicerone<FlowRouter>>(ciceroneQualifier).router
    }
    //endregion
}
