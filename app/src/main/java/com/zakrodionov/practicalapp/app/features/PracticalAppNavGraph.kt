package com.zakrodionov.practicalapp.app.features

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.zakrodionov.practicalapp.app.features.MainDestinations.LOGIN_ROUTE
import com.zakrodionov.practicalapp.app.features.MainDestinations.POST_ID_KEY
import com.zakrodionov.practicalapp.app.features.bottom.ui.HomeScreeens
import com.zakrodionov.practicalapp.app.features.bottom.ui.addHomeGraph
import com.zakrodionov.practicalapp.app.features.login.ui.LoginScreens
import com.zakrodionov.practicalapp.app.features.login.ui.addLoginGraph
import com.zakrodionov.practicalapp.app.features.posts.ui.detail.PostDetailsScreen

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
    startDestination: String = MainDestinations.HOME_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        navigation(
            route = MainDestinations.HOME_ROUTE,
            startDestination = HomeScreeens.POSTS.route
        ) {
            addHomeGraph(
                navigateToPostDetail = { postId: String ->
                    navController.navigate("${MainDestinations.POST_DETAIL_ROUTE}/$postId")
                },
                navigateToLogin = {
                    navController.navigate(LOGIN_ROUTE)
                }
            )
        }

        navigation(
            route = LOGIN_ROUTE,
            startDestination = LoginScreens.PHONE.route
        ) {
            addLoginGraph(
                navigateToPassword = {
                    navController.navigate(LoginScreens.PASSWORD.route)
                },
                popToRoot = {
                    navController.navigateUp()
                }
            )
        }

        composable(
            "${MainDestinations.POST_DETAIL_ROUTE}/{$POST_ID_KEY}",
            arguments = listOf(navArgument(POST_ID_KEY) { type = NavType.StringType })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val postId = arguments.getString(POST_ID_KEY)!!
            PostDetailsScreen(postId)
        }
    }
}