package com.zakrodionov.practicalapp.app.features.about.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.More
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.github.terrakok.cicerone.Screen
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseTabFragment
import com.zakrodionov.practicalapp.app.features.about.AboutScreens.AboutScreen
import com.zakrodionov.practicalapp.app.features.about.di.ABOUT_QUALIFIER
import com.zakrodionov.practicalapp.app.ui.components.TabContent

class AboutFlowFragment : BaseTabFragment(
    R.layout.layout_fragment_container,
    R.id.fragmentContainerView,
    ABOUT_QUALIFIER
) {

    companion object {
        fun newInstance() = AboutFlowFragment()
    }

    override val startScreen: Screen = AboutScreen()
}

object AboutTab : Tab {
    override val key: String
        get() = "AboutTab"

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.More)

            return remember {
                TabOptions(
                    index = 2u,
                    title = "About",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        TabContent(startScreen = com.zakrodionov.practicalapp.app.features.about.ui.about.AboutScreen)
    }
}
