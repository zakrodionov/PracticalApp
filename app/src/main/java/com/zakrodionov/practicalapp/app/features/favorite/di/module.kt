package com.zakrodionov.practicalapp.app.features.favorite.di

import com.github.terrakok.cicerone.Cicerone
import com.zakrodionov.practicalapp.app.core.navigation.FlowRouter
import com.zakrodionov.practicalapp.app.di.DIQualifiers
import com.zakrodionov.practicalapp.app.features.favorite.ui.favorite.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

const val FAVORITE_QUALIFIER = "FAVORITE_QUALIFIER"

val favoriteModule = module {
    val ciceroneQualifier = DIQualifiers.ciceroneQualifier(FAVORITE_QUALIFIER)
    val navigationHolderQualifier = DIQualifiers.navigationHolderQualifier(FAVORITE_QUALIFIER)
    val routerQualifier = DIQualifiers.routerQualifier(FAVORITE_QUALIFIER)

    viewModel { FavoriteViewModel() }

    // region Flow navigation
    single(ciceroneQualifier) {
        Cicerone.create(FlowRouter(get(), routerQualifier.value))
    }

    single(navigationHolderQualifier) {
        get<Cicerone<FlowRouter>>(ciceroneQualifier).getNavigatorHolder()
    }

    single(routerQualifier) {
        get<Cicerone<FlowRouter>>(ciceroneQualifier).router
    }
    //endregion
}
