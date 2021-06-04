package com.zakrodionov.practicalapp.app.features.about.di

import com.zakrodionov.practicalapp.app.core.navigation.flowModule
import com.zakrodionov.practicalapp.app.core.navigation.toRouterQualifier
import com.zakrodionov.practicalapp.app.features.about.ui.about.AboutViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

const val ABOUT_QUALIFIER = "ABOUT_QUALIFIER"

val aboutModule = flowModule(ABOUT_QUALIFIER) {
    viewModel { AboutViewModel(get(), get(), get(ABOUT_QUALIFIER.toRouterQualifier)) }
}
