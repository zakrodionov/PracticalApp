package com.zakrodionov.practicalapp.app.features.about.di

import com.zakrodionov.practicalapp.app.core.navigation.flowModule
import com.zakrodionov.practicalapp.app.features.about.ui.AboutFlowFragment
import com.zakrodionov.practicalapp.app.features.about.ui.about.AboutViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

val aboutModule = flowModule<AboutFlowFragment> {
    viewModel { AboutViewModel(get(), get(), get()) }
}
