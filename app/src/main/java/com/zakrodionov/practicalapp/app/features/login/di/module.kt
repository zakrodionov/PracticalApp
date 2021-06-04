package com.zakrodionov.practicalapp.app.features.login.di

import com.zakrodionov.practicalapp.app.core.navigation.flowModule
import com.zakrodionov.practicalapp.app.core.navigation.toRouterQualifier
import com.zakrodionov.practicalapp.app.features.login.ui.email.EmailViewModel
import com.zakrodionov.practicalapp.app.features.login.ui.password.PasswordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

const val LOGIN_QUALIFIER = "LOGIN_QUALIFIER"

val loginModule = flowModule(LOGIN_QUALIFIER) {
    viewModel { PasswordViewModel(get(LOGIN_QUALIFIER.toRouterQualifier)) }
    viewModel { EmailViewModel(get(LOGIN_QUALIFIER.toRouterQualifier)) }
}
