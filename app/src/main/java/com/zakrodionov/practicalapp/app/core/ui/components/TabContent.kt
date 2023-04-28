package com.zakrodionov.practicalapp.app.core.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.transitions.SlideTransition
import com.zakrodionov.common.extensions.OnLaunched
import com.zakrodionov.common.extensions.debug

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Tab.TabContent(vararg startScreen: Screen) {
    val tabTitle = options.title
    OnLaunched(
        block = { debug("Navigator - Start tab $tabTitle") },
        onDispose = { debug("Navigator - Dispose tab $tabTitle") }
    )

    Navigator(screens = startScreen.toList()) { navigator ->
        SlideTransition(navigator) { screen ->
            screen.Content()
        }
    }
}
