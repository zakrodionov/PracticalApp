package com.zakrodionov.practicalapp.app.di.initializer

import com.zakrodionov.practicalapp.data.local.ApplicationSettings
import org.koin.core.module.Module

object StoragesInitializer : Initializer {

    override fun initialize(appModule: Module) {
        appModule.apply {
            single { ApplicationSettings(get()) }
        }
    }
}
