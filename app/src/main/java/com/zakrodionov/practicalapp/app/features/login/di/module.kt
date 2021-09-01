package com.zakrodionov.practicalapp.app.features.login.di

import com.zakrodionov.practicalapp.app.core.navigation.flowModule
import com.zakrodionov.practicalapp.app.features.login.ui.LoginFlowFragment
import com.zakrodionov.practicalapp.app.features.login.ui.password.PasswordViewModel
import com.zakrodionov.practicalapp.app.features.login.ui.phone.PhoneViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

val loginModule = flowModule<LoginFlowFragment> {
    viewModel { PasswordViewModel() }
    viewModel { PhoneViewModel(get(), get()) }
}
