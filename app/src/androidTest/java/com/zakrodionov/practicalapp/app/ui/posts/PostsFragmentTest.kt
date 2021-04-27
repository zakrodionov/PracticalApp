package com.zakrodionov.practicalapp.app.ui.posts

import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.agoda.kakao.text.KTextView
import com.zakrodionov.practicalapp.R
import org.hamcrest.Matcher
import org.junit.Test
import org.junit.runner.RunWith

class PostsScreen : Screen<PostsScreen>() {
    val list = KRecyclerView(
        builder = { withId(R.id.rvPosts) },
        itemTypeBuilder = { itemType(::Item) }
    )

    class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
        val tvTitle = KTextView(parent) { withId(R.id.tvTitle) }
        val ivPhoto = KImageView(parent) { withId(R.id.ivPhoto) }
    }
}

@RunWith(AndroidJUnit4ClassRunner::class)
internal class PostsFragmentTest {

    @Test
    fun verifyRvVisibilityWhenStatePostsIsEmpty() {
        val state = PostsState(posts = emptyList())
        val scenario = launchFragmentInContainer<PostsFragment>()
        scenario.onFragment {
            it.render(state)
        }
        onScreen<PostsScreen> {
            list {
                isGone()
            }
        }
    }
}
