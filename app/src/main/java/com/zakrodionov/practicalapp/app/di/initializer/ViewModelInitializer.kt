package com.zakrodionov.practicalapp.app.di.initializer

import com.zakrodionov.practicalapp.app.features.home.about.AboutViewModel
import com.zakrodionov.practicalapp.app.features.home.posts.detail.PostDetailViewModel
import com.zakrodionov.practicalapp.app.features.home.posts.list.PostsViewModel
import com.zakrodionov.practicalapp.app.features.login.phone.PhoneViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module

object ViewModelInitializer : Initializer {

    override fun initialize(appModule: Module) {
        appModule.apply {
            viewModel { PostsViewModel(get(), get()) }
            viewModel { parameters -> PostDetailViewModel(get(), get(), parameters.get()) }
            viewModel { PhoneViewModel(get()) }
            viewModel { AboutViewModel(get(), get()) }
        }
    }
}
