package com.zakrodionov.practicalapp.app.di.module

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.zakrodionov.practicalapp.app.core.navigation.GlobalRouter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GlobalNavigationModule {

    @Provides
    fun provideCicerone(): Cicerone<GlobalRouter> = Cicerone.create(GlobalRouter())


    @Provides
    fun provideNavigatorHolder(cicerone: Cicerone<GlobalRouter>): NavigatorHolder = cicerone.getNavigatorHolder()

    @Provides
    fun provideRouter(cicerone: Cicerone<GlobalRouter>): GlobalRouter = cicerone.router

}