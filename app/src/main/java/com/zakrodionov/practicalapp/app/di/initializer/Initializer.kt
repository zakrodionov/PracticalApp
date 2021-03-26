package com.zakrodionov.practicalapp.app.di.initializer

import org.koin.core.module.Module

interface Initializer {
    fun initialize(appModule: Module)
}
