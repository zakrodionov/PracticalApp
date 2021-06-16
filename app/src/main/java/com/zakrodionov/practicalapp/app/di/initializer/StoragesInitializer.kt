package com.zakrodionov.practicalapp.app.di.initializer

import com.zakrodionov.practicalapp.app.data.preferences.AppPreferences
import org.koin.core.module.Module

object StoragesInitializer : Initializer {

    override fun initialize(appModule: Module) {
        appModule.apply {
            single { AppPreferences(get()) }
        }
    }
}
