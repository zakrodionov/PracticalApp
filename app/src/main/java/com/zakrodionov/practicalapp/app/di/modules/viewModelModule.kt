package com.zakrodionov.practicalapp.app.di.modules

import com.zakrodionov.practicalapp.app.features.home.about.AboutViewModel
import com.zakrodionov.practicalapp.app.features.home.posts.detail.PostDetailViewModel
import com.zakrodionov.practicalapp.app.features.home.posts.list.PostsViewModel
import com.zakrodionov.practicalapp.app.features.login.phone.PhoneViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// TODO wait koin fix SavedStateHandle
// val viewModelModule = module {
//    viewModel { PostsViewModel(get(), get(), get()) }
//    viewModel { parameters -> PostDetailViewModel(get(), get(), parameters.get()) }
//    viewModel { PhoneViewModel(get()) }
//    viewModel { AboutViewModel(get(), get()) }
// }

val viewModelModule = module {
    viewModel { PostsViewModel(get(), get()) }
    viewModel { parameters -> PostDetailViewModel(get(), parameters.get()) }
    viewModel { PhoneViewModel() }
    viewModel { AboutViewModel(get()) }
}
