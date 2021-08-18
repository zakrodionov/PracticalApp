package com.zakrodionov.practicalapp.app.features.login.di

import com.zakrodionov.practicalapp.app.features.login.ui.phone.PhoneViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel { PhoneViewModel(get()) }
}
