package com.zakrodionov.practicalapp.app.features.home.posts.di

import com.zakrodionov.practicalapp.app.features.home.posts.data.PostRepositoryImpl
import com.zakrodionov.practicalapp.app.features.home.posts.data.remote.ApiPosts
import com.zakrodionov.practicalapp.app.features.home.posts.domain.PostRepository
import com.zakrodionov.practicalapp.app.features.home.posts.ui.detail.PostDetailViewModel
import com.zakrodionov.practicalapp.app.features.home.posts.ui.list.PostsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val postsModule = module {
    viewModel { PostsViewModel(get(), get()) }
    viewModel { parameters -> PostDetailViewModel(get(), get(), parameters.get()) }

    single<ApiPosts> { get<Retrofit>().create(ApiPosts::class.java) }
    single<PostRepository> { PostRepositoryImpl(get(), get()) }
}
