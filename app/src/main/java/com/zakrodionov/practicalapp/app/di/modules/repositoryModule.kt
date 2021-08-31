package com.zakrodionov.practicalapp.app.di.modules

import com.zakrodionov.practicalapp.app.data.repositories.PostRepositoryImpl
import com.zakrodionov.practicalapp.app.domain.repositories.PostRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<PostRepository> { PostRepositoryImpl(get(), get()) }
}
