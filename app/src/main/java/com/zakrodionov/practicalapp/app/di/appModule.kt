package com.zakrodionov.practicalapp.app.di

import com.zakrodionov.practicalapp.app.di.initializer.CoreInitializer
import com.zakrodionov.practicalapp.app.di.initializer.GlobalNavigationInitializer
import com.zakrodionov.practicalapp.app.di.initializer.NetInitializer
import com.zakrodionov.practicalapp.app.di.initializer.StoragesInitializer
import org.koin.dsl.module

val appModule = module {

    listOf(
        CoreInitializer,
        NetInitializer,
        StoragesInitializer,
        RepositoriesInitializer,
        GlobalNavigationInitializer
    ).forEach {
        it.initialize(this)
    }
}
