package com.zakrodionov.practicalapp.app.features.posts.di

import com.github.terrakok.cicerone.Cicerone
import com.zakrodionov.practicalapp.app.core.navigation.FlowRouter
import com.zakrodionov.practicalapp.app.di.DIQualifiers
import com.zakrodionov.practicalapp.app.di.initializer.NetInitializer.NAME_MAIN_RETROFIT
import com.zakrodionov.practicalapp.app.features.posts.data.PostRepositoryImpl
import com.zakrodionov.practicalapp.app.features.posts.data.remote.ApiPost
import com.zakrodionov.practicalapp.app.features.posts.domain.PostRepository
import com.zakrodionov.practicalapp.app.features.posts.ui.postDetails.PostDetailViewModel
import com.zakrodionov.practicalapp.app.features.posts.ui.postsList.PostsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

const val POSTS_QUALIFIER = "POSTS_QUALIFIER"

val postsModule = module {
    val ciceroneQualifier = DIQualifiers.ciceroneQualifier(POSTS_QUALIFIER)
    val navigationHolderQualifier = DIQualifiers.navigationHolderQualifier(POSTS_QUALIFIER)
    val routerQualifier = DIQualifiers.routerQualifier(POSTS_QUALIFIER)

    viewModel { PostsViewModel(get(), get(), flowRouter = get(routerQualifier)) }
    viewModel { parameters -> PostDetailViewModel(get(), get(), parameters.get()) }

    single<PostRepository> { PostRepositoryImpl(get(), get()) }

    single<ApiPost> { get<Retrofit>(named(NAME_MAIN_RETROFIT)).create(ApiPost::class.java) }

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
