package com.zakrodionov.practicalapp.app.features.posts.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.github.terrakok.cicerone.Screen
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseTabFragment
import com.zakrodionov.practicalapp.app.features.posts.PostsScreens.postsScreen
import com.zakrodionov.practicalapp.app.features.posts.di.POSTS_QUALIFIER
import com.zakrodionov.practicalapp.app.features.posts.ui.list.PostsScreen
import com.zakrodionov.practicalapp.app.ui.components.TabContent

class PostsFlowFragment : BaseTabFragment(
    R.layout.layout_fragment_container,
    R.id.fragmentContainerView,
    POSTS_QUALIFIER
) {

    companion object {
        fun newInstance() = PostsFlowFragment()
    }

    override val startScreen: Screen = postsScreen()
}

object PostsTab : Tab {
    override val key: String
        get() = "PostsTab"

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.List)

            return remember {
                TabOptions(
                    index = 0u,
                    title = "Posts",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        TabContent(startScreen = PostsScreen)
    }
}
