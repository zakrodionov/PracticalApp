package com.zakrodionov.practicalapp.app.features.posts.di

import com.zakrodionov.practicalapp.app.core.navigation.flowModule
import com.zakrodionov.practicalapp.app.features.posts.data.PostRepositoryImpl
import com.zakrodionov.practicalapp.app.features.posts.data.remote.ApiPosts
import com.zakrodionov.practicalapp.app.features.posts.domain.PostRepository
import com.zakrodionov.practicalapp.app.features.posts.ui.PostsFlowFragment
import com.zakrodionov.practicalapp.app.features.posts.ui.detail.PostDetailViewModel
import com.zakrodionov.practicalapp.app.features.posts.ui.list.PostsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import retrofit2.Retrofit

val postsModule = flowModule<PostsFlowFragment> {
    viewModel { PostsViewModel(get(), get(), get()) }
    viewModel { parameters -> PostDetailViewModel(get(), get(), parameters.get()) }

    scoped<ApiPosts> { get<Retrofit>().create(ApiPosts::class.java) }
    scoped<PostRepository> { PostRepositoryImpl(get(), get()) }
}
