package com.zakrodionov.practicalapp.app.di

import com.zakrodionov.practicalapp.app.di.initializer.CoreInitializer
import com.zakrodionov.practicalapp.app.di.initializer.NetInitializer
import com.zakrodionov.practicalapp.app.di.initializer.RepositoriesInitializer
import com.zakrodionov.practicalapp.app.di.initializer.StoragesInitializer
import com.zakrodionov.practicalapp.app.di.initializer.ViewModelsInitializer
import org.koin.dsl.module

val appModule = module {

    listOf(
        CoreInitializer,
        NetInitializer,
        StoragesInitializer,
        RepositoriesInitializer,
        ViewModelsInitializer,
    ).forEach {
        it.initialize(this)
    }
}
