package com.zakrodionov.practicalapp.app.features.home.about.di

import com.zakrodionov.practicalapp.app.features.home.about.ui.about.AboutViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val aboutModule = module {
    viewModel { AboutViewModel(get(), get()) }
}
