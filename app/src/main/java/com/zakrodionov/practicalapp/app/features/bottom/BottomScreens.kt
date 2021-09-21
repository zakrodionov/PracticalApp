package com.zakrodionov.practicalapp.app.features.bottom

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.zakrodionov.practicalapp.app.features.bottom.ui.tabs.ArgsBottomTabs
import com.zakrodionov.practicalapp.app.features.bottom.ui.tabs.BottomTabsFragment

@Suppress("FunctionName")
object BottomScreens {
    fun BottomTabsScreen(args: ArgsBottomTabs = ArgsBottomTabs()) = FragmentScreen("BottomTabsScreen") {
        BottomTabsFragment.newInstance(args)
    }
}
