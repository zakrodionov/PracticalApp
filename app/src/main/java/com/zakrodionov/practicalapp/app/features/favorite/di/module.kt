package com.zakrodionov.practicalapp.app.features.favorite.di

import com.zakrodionov.practicalapp.app.core.navigation.flowModule
import com.zakrodionov.practicalapp.app.features.favorite.ui.FavoriteFlowFragment
import com.zakrodionov.practicalapp.app.features.favorite.ui.favorite.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

val favoriteModule = flowModule<FavoriteFlowFragment> {
    viewModel { FavoriteViewModel() }
}
