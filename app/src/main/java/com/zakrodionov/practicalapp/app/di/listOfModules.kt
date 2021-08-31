package com.zakrodionov.practicalapp.app.di

import com.zakrodionov.practicalapp.app.di.modules.coreModule
import com.zakrodionov.practicalapp.app.di.modules.globalNavigationModule
import com.zakrodionov.practicalapp.app.di.modules.netModule
import com.zakrodionov.practicalapp.app.di.modules.storagesModule
import com.zakrodionov.practicalapp.app.features.about.di.aboutModule
import com.zakrodionov.practicalapp.app.features.bottom.di.bottomTabsModule
import com.zakrodionov.practicalapp.app.features.favorite.di.favoriteModule
import com.zakrodionov.practicalapp.app.features.login.di.loginModule
import com.zakrodionov.practicalapp.app.features.posts.di.postsModule

val appScopeModules = listOf(
    coreModule,
    globalNavigationModule,
    netModule,
    storagesModule
)

val featuresModules = listOf(
    bottomTabsModule,
    postsModule,
    favoriteModule,
    aboutModule,
    loginModule,
)

val listOfModules = appScopeModules + featuresModules


