package com.zakrodionov.practicalapp.app.features.home

import android.os.Parcelable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.More
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.zakrodionov.practicalapp.app.core.ui.components.TabContent
import com.zakrodionov.practicalapp.app.features.home.about.AboutScreen
import com.zakrodionov.practicalapp.app.features.home.favorite.FavoriteScreen
import com.zakrodionov.practicalapp.app.features.home.posts.list.PostsScreen
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostsTab(private val innerScreens: List<Screen> = listOf(PostsScreen())) : Tab, Parcelable {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.List)
            return remember { TabOptions(index = 0u, title = "Posts", icon = icon) }
        }

    @Composable
    override fun Content() {
        TabContent(startScreen = innerScreens.toTypedArray())
    }
}

@Parcelize
data class FavoritesTab(private val innerScreens: List<Screen> = listOf(FavoriteScreen())) : Tab, Parcelable {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Favorite)
            return remember {
                TabOptions(index = 1u, title = "Favorites", icon = icon)
            }
        }

    @Composable
    override fun Content() {
        TabContent(startScreen = innerScreens.toTypedArray())
    }
}

@Parcelize
data class AboutTab(private val innerScreens: List<Screen> = listOf(AboutScreen())) : Tab, Parcelable {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.More)
            return remember { TabOptions(index = 2u, title = "About", icon = icon) }
        }

    @Composable
    override fun Content() {
        TabContent(startScreen = innerScreens.toTypedArray())
    }
}
