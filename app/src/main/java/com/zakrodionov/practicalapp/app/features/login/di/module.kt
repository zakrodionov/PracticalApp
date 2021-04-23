package com.zakrodionov.practicalapp.app.features.login.di

import com.github.terrakok.cicerone.Cicerone
import com.zakrodionov.practicalapp.app.core.navigation.FlowRouter
import com.zakrodionov.practicalapp.app.di.DIQualifiers
import com.zakrodionov.practicalapp.app.features.login.ui.email.EmailViewModel
import com.zakrodionov.practicalapp.app.features.login.ui.password.PasswordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

const val LOGIN_QUALIFIER = "LOGIN_QUALIFIER"

val loginModule = module {
    val ciceroneQualifier = DIQualifiers.ciceroneQualifier(LOGIN_QUALIFIER)
    val navigationHolderQualifier = DIQualifiers.navigationHolderQualifier(LOGIN_QUALIFIER)
    val routerQualifier = DIQualifiers.routerQualifier(LOGIN_QUALIFIER)

    viewModel { PasswordViewModel(get(routerQualifier)) }
    viewModel { EmailViewModel(get(routerQualifier)) }

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
