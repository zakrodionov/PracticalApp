package com.zakrodionov.practicalapp.app.features.favorite.ui

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.github.terrakok.cicerone.Screen
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseTabFragment
import com.zakrodionov.practicalapp.app.features.favorite.FavoriteScreens.FavoriteScreen
import com.zakrodionov.practicalapp.app.features.favorite.di.FAVORITE_QUALIFIER

class FavoriteFlowFragment : BaseTabFragment(
    R.layout.layout_fragment_container,
    R.id.fragmentContainerView,
    FAVORITE_QUALIFIER
) {

    companion object {
        fun newInstance() = FavoriteFlowFragment()
    }

    override val startScreen: Screen = FavoriteScreen()
}
