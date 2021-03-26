package com.zakrodionov.practicalapp.app.di.initializer

import com.zakrodionov.practicalapp.data.repository.PostRepositoryImpl
import com.zakrodionov.practicalapp.domain.repository.PostRepository
import org.koin.core.module.Module

object RepositoriesInitializer : Initializer {

    override fun initialize(appModule: Module) {
        appModule.apply {
            single<PostRepository> { PostRepositoryImpl(get(), get()) }
        }
    }
}
