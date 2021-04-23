package com.zakrodionov.practicalapp.app.features.about

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.zakrodionov.practicalapp.app.features.about.ui.AboutFlowFragment
import com.zakrodionov.practicalapp.app.features.about.ui.about.AboutFragment

@Suppress("FunctionName")
object AboutScreens {
    fun AboutFlowScreen() = FragmentScreen("AboutFlowScreen") {
        AboutFlowFragment.newInstance()
    }

    fun AboutScreen() = FragmentScreen("AboutScreen") {
        AboutFragment()
    }
}
