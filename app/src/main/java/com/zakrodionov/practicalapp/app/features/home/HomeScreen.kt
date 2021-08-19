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
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navArgument
import androidx.navigation.navigation
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.features.MainDestinations
import com.zakrodionov.practicalapp.app.features.home.HomeScreen.Keys.POST_ID_KEY
import com.zakrodionov.practicalapp.app.features.home.HomeTabScreen.AboutTab
import com.zakrodionov.practicalapp.app.features.home.HomeTabScreen.FavoriteTab
import com.zakrodionov.practicalapp.app.features.home.HomeTabScreen.PostsTab
import com.zakrodionov.practicalapp.app.features.home.about.AboutScreen
import com.zakrodionov.practicalapp.app.features.home.favorite.FavoriteScreen
import com.zakrodionov.practicalapp.app.features.home.posts.detail.PostDetailsScreen
import com.zakrodionov.practicalapp.app.features.home.posts.list.PostsScreen

sealed class HomeScreen(
    private val route: String,
) {
    companion object Keys {
        const val POST_ID_KEY = "postId"
    }

    fun createRoute(root: HomeTabScreen) = "${root.route}/$route"

    object Posts : HomeScreen("posts")
    object PostDetail : HomeScreen("post_detail/{$POST_ID_KEY}") {
        fun createRoute(root: HomeTabScreen, postId: String): String {
            return "${root.route}/post_detail/$postId"
        }
    }

    object Favorite : HomeScreen("favorite")
    object About : HomeScreen("about")
}

enum class HomeTabScreen(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String,
) {
    PostsTab(R.string.posts, Icons.Outlined.List, "posts_tab"),
    FavoriteTab(R.string.favorite, Icons.Outlined.Favorite, "favorite_tab"),
    AboutTab(R.string.about, Icons.Outlined.MoreHoriz, "about_tab"),
}

fun NavGraphBuilder.addHomeGraph(navController: NavHostController) {
    addPostsGraph(navController)
    addFavoriteGraph(navController)
    addAboutGraph(navController)
}

fun NavGraphBuilder.addPostsGraph(navController: NavController) {
    navigation(
        route = PostsTab.route,
        startDestination = HomeScreen.Posts.createRoute(PostsTab),
    ) {
        addPosts(navController, PostsTab)
        addPostDetail(PostsTab)
    }
}

fun NavGraphBuilder.addFavoriteGraph(navController: NavController) {
    navigation(
        route = FavoriteTab.route,
        startDestination = HomeScreen.Favorite.createRoute(FavoriteTab),
    ) {
        addFavorite(navController, FavoriteTab)
        addPostDetail(FavoriteTab)
    }
}

fun NavGraphBuilder.addAboutGraph(navController: NavController) {
    navigation(
        route = AboutTab.route,
        startDestination = HomeScreen.About.createRoute(AboutTab),
    ) {
        addAbout(navController, AboutTab)
    }
}

fun NavGraphBuilder.addPosts(navController: NavController, root: HomeTabScreen) {
    composable(HomeScreen.Posts.createRoute(root)) {
        PostsScreen {
            navController.navigate(HomeScreen.PostDetail.createRoute(root, it))
        }
    }
}

// Пример экрана который открывается с разных графов
fun NavGraphBuilder.addPostDetail(root: HomeTabScreen) {
    composable(
        HomeScreen.PostDetail.createRoute(root),
        arguments = listOf(navArgument(POST_ID_KEY) { type = NavType.StringType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val postId = requireNotNull(arguments.getString(POST_ID_KEY))
        PostDetailsScreen(postId)
    }
}

fun NavGraphBuilder.addFavorite(navController: NavController, root: HomeTabScreen) {
    composable(HomeScreen.Favorite.createRoute(root)) {
        FavoriteScreen(
            navigateToPostDetail = {
                navController.navigate(HomeScreen.PostDetail.createRoute(root, it))
            },
            navigateToLogin = {
                navController.navigate(MainDestinations.LOGIN_ROUTE)
            }
        )
    }
}

fun NavGraphBuilder.addAbout(navController: NavController, root: HomeTabScreen) {
    composable(HomeScreen.About.createRoute(root)) {
        AboutScreen {
            navController.navigate(MainDestinations.LOGIN_ROUTE)
        }
    }
}

@Composable
fun PracticalAppBottomBar(
    navController: NavController,
    tabs: Array<HomeTabScreen>,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        tabs.forEach { tab ->
            BottomNavigationItem(
                icon = { Icon(tab.icon, contentDescription = null) },
                label = { Text(stringResource(tab.title)) },
                selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true,
                onClick = {
                    navController.navigate(tab.route) {
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
