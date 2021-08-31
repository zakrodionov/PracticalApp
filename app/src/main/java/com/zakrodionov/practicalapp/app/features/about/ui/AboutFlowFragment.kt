package com.zakrodionov.practicalapp.app.features.about.ui

import com.github.terrakok.cicerone.Screen
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseTabFragment
import com.zakrodionov.practicalapp.app.features.about.AboutScreens.AboutScreen

class AboutFlowFragment : BaseTabFragment(
    R.layout.layout_fragment_container,
    R.id.fragmentContainerView,
) {

    companion object {
        fun newInstance() = AboutFlowFragment()
    }

    override val startScreen: Screen = AboutScreen()
}
