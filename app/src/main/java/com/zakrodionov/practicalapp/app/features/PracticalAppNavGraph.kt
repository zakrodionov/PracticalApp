@file:Suppress("MatchingDeclarationName")

package com.zakrodionov.practicalapp.app.features

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.zakrodionov.practicalapp.app.features.MainDestinations.HOME_ROUTE
import com.zakrodionov.practicalapp.app.features.MainDestinations.LOGIN_ROUTE
import com.zakrodionov.practicalapp.app.features.home.HomeTabScreens
import com.zakrodionov.practicalapp.app.features.home.addHomeGraph
import com.zakrodionov.practicalapp.app.features.login.LoginScreens
import com.zakrodionov.practicalapp.app.features.login.addLoginGraph

object MainDestinations {
    const val HOME_ROUTE = "home"
    const val LOGIN_ROUTE = "login"
}

@Composable
fun PracticalAppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = HOME_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        navigation(
            route = HOME_ROUTE,
            startDestination = HomeTabScreens.POSTS_TAB.route
        ) {
            addHomeGraph(navController)
        }

        navigation(
            route = LOGIN_ROUTE,
            startDestination = LoginScreens.PHONE.route
        ) {
            addLoginGraph(navController)
        }
    }
}

fun NavHostController.closeNestedNavGraph(navGraphBuilder: NavGraphBuilder) =
    popBackStack(navGraphBuilder.route!!, true)
