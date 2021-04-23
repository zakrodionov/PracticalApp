package com.zakrodionov.practicalapp.app.features.about.ui

import com.github.terrakok.cicerone.Screen
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseTabFragment
import com.zakrodionov.practicalapp.app.features.about.AboutScreens.AboutScreen
import com.zakrodionov.practicalapp.app.features.about.di.ABOUT_QUALIFIER

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
