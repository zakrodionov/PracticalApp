package com.zakrodionov.practicalapp.app.di.modules

import com.zakrodionov.practicalapp.app.data.preferences.AppPreferences
import org.koin.dsl.module

val storagesModule = module {
    single { AppPreferences(get()) }
}
