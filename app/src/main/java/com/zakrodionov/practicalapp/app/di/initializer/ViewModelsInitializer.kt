package com.zakrodionov.practicalapp.app.di.initializer

import com.zakrodionov.practicalapp.app.ui.FavoriteViewModel
import com.zakrodionov.practicalapp.app.ui.about.AboutViewModel
import com.zakrodionov.practicalapp.app.ui.postDetails.PostDetailViewModel
import com.zakrodionov.practicalapp.app.ui.posts.PostsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module

object ViewModelsInitializer : Initializer {

    override fun initialize(appModule: Module) {
        appModule.apply {
            viewModel { PostsViewModel(get(), get(), get()) }
            viewModel { FavoriteViewModel() }
            viewModel { AboutViewModel(get(), get(), get()) }
            viewModel { parameters -> PostDetailViewModel(get(), get(), parameters.get()) }
        }
    }
}
