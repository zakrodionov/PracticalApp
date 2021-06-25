package com.zakrodionov.practicalapp.app.features.posts.di

import com.zakrodionov.practicalapp.app.core.navigation.flowModule
import com.zakrodionov.practicalapp.app.core.navigation.toRouterQualifier
import com.zakrodionov.practicalapp.app.features.posts.data.PostRepositoryImpl
import com.zakrodionov.practicalapp.app.features.posts.data.remote.ApiPosts
import com.zakrodionov.practicalapp.app.features.posts.domain.PostRepository
import com.zakrodionov.practicalapp.app.features.posts.ui.detail.PostDetailViewModel
import com.zakrodionov.practicalapp.app.features.posts.ui.list.PostsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import retrofit2.Retrofit

const val POSTS_QUALIFIER = "POSTS_QUALIFIER"

val postsModule = flowModule(POSTS_QUALIFIER) {
    viewModel { PostsViewModel(get(), get(), flowRouter = get(POSTS_QUALIFIER.toRouterQualifier)) }
    viewModel { parameters -> PostDetailViewModel(get(), get(), parameters.get()) }

    single<ApiPosts> { get<Retrofit>().create(ApiPosts::class.java) }
    single<PostRepository> { PostRepositoryImpl(get(), get()) }
}
