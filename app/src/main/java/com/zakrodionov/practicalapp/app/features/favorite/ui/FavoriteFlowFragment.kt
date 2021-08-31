package com.zakrodionov.practicalapp.app.features.favorite.ui

import com.github.terrakok.cicerone.Screen
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseTabFragment
import com.zakrodionov.practicalapp.app.features.favorite.FavoriteScreens.FavoriteScreen

class FavoriteFlowFragment : BaseTabFragment(
    R.layout.layout_fragment_container,
    R.id.fragmentContainerView,
) {

    companion object {
        fun newInstance() = FavoriteFlowFragment()
    }

    override val startScreen: Screen = FavoriteScreen()
}
