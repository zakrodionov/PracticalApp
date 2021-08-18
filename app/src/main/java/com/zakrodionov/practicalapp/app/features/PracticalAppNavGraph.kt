@file:Suppress("MatchingDeclarationName")

package com.zakrodionov.practicalapp.app.features

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.zakrodionov.practicalapp.app.features.MainDestinations.HOME_ROUTE
import com.zakrodionov.practicalapp.app.features.MainDestinations.LOGIN_ROUTE
import com.zakrodionov.practicalapp.app.features.MainDestinations.POST_DETAIL_ROUTE
import com.zakrodionov.practicalapp.app.features.MainDestinations.POST_ID_KEY
import com.zakrodionov.practicalapp.app.features.home.HomeScreens
import com.zakrodionov.practicalapp.app.features.home.addHomeGraph
import com.zakrodionov.practicalapp.app.features.home.posts.ui.detail.PostDetailsScreen
import com.zakrodionov.practicalapp.app.features.login.LoginScreens
import com.zakrodionov.practicalapp.app.features.login.addLoginGraph

object MainDestinations {
    const val HOME_ROUTE = "home"

    const val LOGIN_ROUTE = "login"

    const val POST_DETAIL_ROUTE = "post"
    const val POST_ID_KEY = "postId"
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
            startDestination = HomeScreens.POSTS.route
        ) {
            addHomeGraph(navController)
        }

        navigation(
            route = LOGIN_ROUTE,
            startDestination = LoginScreens.PHONE.route
        ) {
            addLoginGraph(navController)
        }

        composable(
            "$POST_DETAIL_ROUTE/{$POST_ID_KEY}",
            arguments = listOf(navArgument(POST_ID_KEY) { type = NavType.StringType })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val postId = requireNotNull(arguments.getString(POST_ID_KEY))
            PostDetailsScreen(postId)
        }
    }
}

fun NavHostController.closeNestedNavGraph(navGraphBuilder: NavGraphBuilder) =
    popBackStack(navGraphBuilder.route!!, true)
