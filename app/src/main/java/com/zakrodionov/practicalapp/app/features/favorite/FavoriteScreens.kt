package com.zakrodionov.practicalapp.app.features.favorite

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.zakrodionov.practicalapp.app.features.favorite.ui.FavoriteFlowFragment
import com.zakrodionov.practicalapp.app.features.favorite.ui.favorite.FavoriteFragment

@Suppress("FunctionName")
object FavoriteScreens {
    fun FavoriteFlowScreen() = FragmentScreen("FavoriteFlowScreen") {
        FavoriteFlowFragment.newInstance()
    }

    fun FavoriteScreen() = FragmentScreen("FavoriteScreen") {
        FavoriteFragment()
    }
}
