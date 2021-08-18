package com.zakrodionov.practicalapp.app.features

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zakrodionov.practicalapp.app.features.bottom.ui.HomeSections
import com.zakrodionov.practicalapp.app.features.bottom.ui.PracticalAppBottomBar
import com.zakrodionov.practicalapp.app.features.login.ui.LoginScreens
import com.zakrodionov.practicalapp.app.ui.theme.PracticalAppTheme

@Composable
fun PracticalApp() {
    PracticalAppTheme {
        val tabs = remember { HomeSections.values() }
        val navController = rememberNavController()
        Scaffold(
            bottomBar = {
                if (shouldShowBottomBar(navController)) {
                    PracticalAppBottomBar(navController = navController, tabs = tabs)
                }
            },
            content = { innerPaddingModifier ->
                PracticalAppNavGraph(
                    navController = navController,
                    modifier = Modifier.padding(innerPaddingModifier)
                )
            })
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun shouldShowBottomBar(navController: NavHostController): Boolean {
    val currentRoute = currentRoute(navController)
    return currentRoute != LoginScreens.PHONE.route
            && currentRoute != LoginScreens.PASSWORD.route
}