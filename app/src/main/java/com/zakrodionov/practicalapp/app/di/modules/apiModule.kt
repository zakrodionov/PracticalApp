package com.zakrodionov.practicalapp.app.di.modules

import com.zakrodionov.practicalapp.app.data.api.ApiPosts
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single<ApiPosts> { get<Retrofit>().create(ApiPosts::class.java) }
}
