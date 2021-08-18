/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zakrodionov.practicalapp.app.features.bottom.ui

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
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.features.about.ui.about.AboutScreen
import com.zakrodionov.practicalapp.app.features.favorite.ui.favorite.FavoriteScreen
import com.zakrodionov.practicalapp.app.features.posts.ui.list.PostsScreen

fun NavGraphBuilder.addHomeGraph(
    navigateToPostDetail: (String) -> Unit,
    navigateToLogin: () -> Unit,
) {
    composable(HomeScreeens.POSTS.route) {
        PostsScreen { navigateToPostDetail(it.id.orEmpty()) }
    }
    composable(HomeScreeens.FAVORITE.route) {
        FavoriteScreen()
    }
    composable(HomeScreeens.ABOUT.route) {
        AboutScreen(navigateToLogin)
    }
}

enum class HomeScreeens(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String,
) {
    POSTS(R.string.posts, Icons.Outlined.List, "home/posts"),
    FAVORITE(R.string.favorite, Icons.Outlined.Favorite, "home/favorite"),
    ABOUT(R.string.about, Icons.Outlined.MoreHoriz, "home/about"),
}

@Composable
fun PracticalAppBottomBar(
    navController: NavController,
    tabs: Array<HomeScreeens>,
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val sections = remember { HomeScreeens.values() }
    val routes = remember { sections.map { it.route } }

    BottomNavigation {
        sections.forEach { screen ->
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