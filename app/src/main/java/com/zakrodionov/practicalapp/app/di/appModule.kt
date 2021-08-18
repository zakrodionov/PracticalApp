package com.zakrodionov.practicalapp.app.di

import com.zakrodionov.practicalapp.app.di.initializer.ApiInitializer
import com.zakrodionov.practicalapp.app.di.initializer.CoreInitializer
import com.zakrodionov.practicalapp.app.di.initializer.NetInitializer
import com.zakrodionov.practicalapp.app.di.initializer.RepositoryInitializer
import com.zakrodionov.practicalapp.app.di.initializer.StoragesInitializer
import com.zakrodionov.practicalapp.app.di.initializer.ViewModelInitializer
import org.koin.dsl.module

val appModule = module {

    listOf(
        CoreInitializer,
        NetInitializer,
        StoragesInitializer,
        ApiInitializer,
        RepositoryInitializer,
        ViewModelInitializer
    ).forEach {
        it.initialize(this)
    }
}
