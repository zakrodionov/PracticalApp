package com.zakrodionov.practicalapp.app.features.home

import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.features.home.about.AboutScreen
import com.zakrodionov.practicalapp.app.features.home.favorite.FavoriteScreen
import com.zakrodionov.practicalapp.app.features.home.posts.list.PostsScreen

enum class HomeScreens(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String,
) {
    POSTS(R.string.posts, Icons.Outlined.List, "home/posts"),
    FAVORITE(R.string.favorite, Icons.Outlined.Favorite, "home/favorite"),
    ABOUT(R.string.about, Icons.Outlined.MoreHoriz, "home/about"),
}

fun NavGraphBuilder.addHomeGraph(
    navController: NavHostController,
) {
    composable(HomeScreens.POSTS.route) {
        PostsScreen(navController)
    }
    composable(HomeScreens.FAVORITE.route) {
        FavoriteScreen()
    }
    composable(HomeScreens.ABOUT.route) {
        AboutScreen(navController)
    }
}

@Composable
fun PracticalAppBottomBar(
    navController: NavController,
    tabs: Array<HomeScreens>,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // val routes = remember { tabs.map { it.route } }

    BottomNavigation {
        tabs.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(stringResource(screen.title)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}
