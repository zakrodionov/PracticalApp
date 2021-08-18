package com.zakrodionov.practicalapp.app.di.initializer

import com.zakrodionov.practicalapp.app.data.repositories.PostRepositoryImpl
import com.zakrodionov.practicalapp.app.domain.repositories.PostRepository
import org.koin.core.module.Module

object RepositoryInitializer : Initializer {

    override fun initialize(appModule: Module) {
        appModule.apply {
            single<PostRepository> { PostRepositoryImpl(get(), get()) }
        }
    }
}
