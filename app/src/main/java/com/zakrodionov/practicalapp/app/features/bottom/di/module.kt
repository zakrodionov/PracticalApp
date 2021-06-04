package com.zakrodionov.practicalapp.app.features.bottom.di

import com.zakrodionov.practicalapp.app.core.navigation.tabFlowModule
import com.zakrodionov.practicalapp.app.core.navigation.toRouterQualifier
import com.zakrodionov.practicalapp.app.features.bottom.ui.tabs.BottomTabsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

const val BOTTOM_TABS_QUALIFIER = "BOTTOM_TABS_QUALIFIER"

val bottomTabsModule = tabFlowModule(BOTTOM_TABS_QUALIFIER) {
    viewModel { BottomTabsViewModel(get(), get(BOTTOM_TABS_QUALIFIER.toRouterQualifier)) }
}
