package com.zakrodionov.practicalapp.app.ui.login

import com.zakrodionov.practicalapp.app.core.navigation.FlowModoRouter
import com.zakrodionov.practicalapp.app.core.navigation.buildModo
import com.zakrodionov.practicalapp.app.ui.login.email.EmailViewModel
import com.zakrodionov.practicalapp.app.ui.login.password.PasswordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginFlowModule = module {

    scope<LoginFlowFragment> {

        scoped { FlowModoRouter(get(), buildModo(get())) }

        viewModel { PasswordViewModel(get()) }
        viewModel { EmailViewModel(get()) }
    }
}
