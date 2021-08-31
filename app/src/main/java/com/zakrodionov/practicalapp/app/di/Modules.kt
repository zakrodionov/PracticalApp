package com.zakrodionov.practicalapp.app.di

import com.zakrodionov.practicalapp.app.di.modules.apiModule
import com.zakrodionov.practicalapp.app.di.modules.coreModule
import com.zakrodionov.practicalapp.app.di.modules.netModule
import com.zakrodionov.practicalapp.app.di.modules.repositoryModule
import com.zakrodionov.practicalapp.app.di.modules.storagesModule
import com.zakrodionov.practicalapp.app.di.modules.viewModelModule

val allModules = listOf(
    coreModule,
    viewModelModule,
    netModule,
    storagesModule,
    apiModule,
    repositoryModule,
)
