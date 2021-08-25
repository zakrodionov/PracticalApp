package com.zakrodionov.practicalapp.app.core.navigation

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import cafe.adriel.voyager.navigator.Navigator

// Роутер для навигации поверх Bottom Tab Bar
val LocalGlobalNavigator: ProvidableCompositionLocal<Navigator> =
    staticCompositionLocalOf { error("LocalGlobalNavigator not initialized") }
