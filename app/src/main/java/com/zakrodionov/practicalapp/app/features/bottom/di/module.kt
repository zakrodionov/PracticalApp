package com.zakrodionov.practicalapp.app.features.bottom.di

import com.zakrodionov.practicalapp.app.core.navigation.tabFlowModule
import com.zakrodionov.practicalapp.app.features.bottom.ui.tabs.BottomTabsFragment
import com.zakrodionov.practicalapp.app.features.bottom.ui.tabs.BottomTabsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

val bottomTabsModule = tabFlowModule<BottomTabsFragment> {
    viewModel { BottomTabsViewModel(get(), get()) }
}
