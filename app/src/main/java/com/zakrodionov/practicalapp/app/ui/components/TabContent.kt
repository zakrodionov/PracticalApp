package com.zakrodionov.practicalapp.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.transitions.SlideTransition
import com.zakrodionov.common.extensions.debug

@Composable
fun Tab.TabContent(startScreen: Screen) {
    val tabTitle = options.title

    LifecycleEffect(
        onStarted = { debug("Navigator - Start tab $tabTitle") },
        onDisposed = { debug("Navigator - Dispose tab $tabTitle") },
    )

    Navigator(screen = startScreen) { navigator ->
        SlideTransition(navigator) { screen ->
            Column {
                screen.Content()
                debug("Navigator - Last Event: ${navigator.lastEvent}")
            }
        }
    }
}
