package com.zakrodionov.practicalapp.app.di.initializer

import com.zakrodionov.practicalapp.app.data.api.ApiPosts
import org.koin.core.module.Module
import retrofit2.Retrofit

object ApiInitializer : Initializer {

    override fun initialize(appModule: Module) {
        appModule.apply {
            single<ApiPosts> { get<Retrofit>().create(ApiPosts::class.java) }
        }
    }
}
