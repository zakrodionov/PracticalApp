package com.zakrodionov.practicalapp.app.features.favorite.di

import com.zakrodionov.practicalapp.app.core.navigation.flowModule
import com.zakrodionov.practicalapp.app.features.favorite.ui.favorite.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

const val FAVORITE_QUALIFIER = "FAVORITE_QUALIFIER"

val favoriteModule = flowModule(FAVORITE_QUALIFIER) {
    viewModel { FavoriteViewModel() }
}
