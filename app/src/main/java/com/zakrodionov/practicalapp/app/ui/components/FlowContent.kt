package com.zakrodionov.practicalapp.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.zakrodionov.common.extensions.debug
import com.zakrodionov.practicalapp.app.core.navigation.Flow

@Composable
fun Flow.FlowContent(startScreen: Screen) {
    DisposableEffect(Unit) {
        debug("Navigator - Start flow $title")
        onDispose { debug("Navigator - Dispose flow $title") }
    }

    Navigator(screen = startScreen) { navigator ->
        SlideTransition(navigator) { screen ->
            Column {
                screen.Content()
                debug("Navigator - Last Event: ${navigator.lastEvent}")
            }
        }
    }
}
