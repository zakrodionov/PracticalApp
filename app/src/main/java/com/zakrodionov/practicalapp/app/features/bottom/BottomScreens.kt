package com.zakrodionov.practicalapp.app.features.bottom

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.zakrodionov.practicalapp.app.features.bottom.ui.tabs.BottomTabsFragment

@Suppress("FunctionName")
object BottomScreens {
    fun BottomTabsScreen() = FragmentScreen("BottomTabsScreen") {
        BottomTabsFragment.newInstance()
    }
}
