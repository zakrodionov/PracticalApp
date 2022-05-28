package com.zakrodionov.practicalapp.app.features.bottom.base

import android.os.Parcelable
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.features.about.ui.AboutFlowFragment
import com.zakrodionov.practicalapp.app.features.favorite.ui.FavoriteFlowFragment
import com.zakrodionov.practicalapp.app.features.posts.ui.PostsFlowFragment
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Tab(val menuItemId: Int) : Parcelable {
    POSTS(R.id.posts) {
        override fun getFragment(): Fragment = PostsFlowFragment.newInstance()
    },
    FAVORITE(R.id.favorite) {
        override fun getFragment(): Fragment = FavoriteFlowFragment.newInstance()
    },
    ABOUT(R.id.about) {
        override fun getFragment(): Fragment = AboutFlowFragment.newInstance()
    };

    abstract fun getFragment(): Fragment

    companion object {
        val DEFAULT_TAB = POSTS

        private val mapByItemId = values().associateBy(Tab::menuItemId)
        private val mapByName = values().associateBy(Tab::name)

        fun from(menuItem: MenuItem): Tab {
            return mapByItemId[menuItem.itemId]
                ?: throw IllegalStateException("No nav item for such itemId ${menuItem.itemId}")
        }

        fun from(menuItemId: Int): Tab {
            return mapByItemId[menuItemId]
                ?: throw IllegalStateException("No nav item for such itemId $menuItemId")
        }

        fun from(fragment: Fragment): Tab {
            return mapByName[fragment.tag]
                ?: throw IllegalStateException("No nav item for such fragment tag ${fragment.tag}")
        }
    }
}
