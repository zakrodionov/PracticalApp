package com.zakrodionov.practicalapp.app.features.bottom.di

import com.github.terrakok.cicerone.Cicerone
import com.zakrodionov.practicalapp.app.core.navigation.TabFlowRouter
import com.zakrodionov.practicalapp.app.di.DIQualifiers
import com.zakrodionov.practicalapp.app.features.bottom.ui.tabs.BottomTabsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

const val BOTTOM_TABS_QUALIFIER = "BOTTOM_TABS_QUALIFIER"

val bottomTabsModule = module {
    val ciceroneQualifier = DIQualifiers.ciceroneQualifier(BOTTOM_TABS_QUALIFIER)
    val navigationHolderQualifier = DIQualifiers.navigationHolderQualifier(BOTTOM_TABS_QUALIFIER)
    val routerQualifier = DIQualifiers.routerQualifier(BOTTOM_TABS_QUALIFIER)

    viewModel { BottomTabsViewModel(get(), get(routerQualifier)) }

    // region Flow navigation
    single(ciceroneQualifier) {
        Cicerone.create(TabFlowRouter())
    }

    single(navigationHolderQualifier) {
        get<Cicerone<TabFlowRouter>>(ciceroneQualifier).getNavigatorHolder()
    }

    single(routerQualifier) {
        get<Cicerone<TabFlowRouter>>(ciceroneQualifier).router
    }
    //endregion
}
