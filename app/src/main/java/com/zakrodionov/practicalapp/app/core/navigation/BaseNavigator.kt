package com.zakrodionov.practicalapp.app.core.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.zakrodionov.common.extensions.setHorizontalSlideAnimations
import com.zakrodionov.common.extensions.setVerticalSlideAnimations
import com.zakrodionov.practicalapp.app.core.navigation.ScreenAnimationStrategy.NONE
import com.zakrodionov.practicalapp.app.core.navigation.ScreenAnimationStrategy.SLIDE_HORIZONTAL
import com.zakrodionov.practicalapp.app.core.navigation.ScreenAnimationStrategy.SLIDE_VERTICAL
import com.zakrodionov.practicalapp.app.features.bottom.ui.tabs.BottomTabsFragment

// Обычный навигатор между экранами
open class BaseNavigator(
    activity: FragmentActivity,
    fragmentManager: FragmentManager,
    @IdRes containerId: Int
) : AppNavigator(activity, containerId, fragmentManager) {

    override fun setupFragmentTransaction(
        screen: FragmentScreen,
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment
    ) {
        val animationStrategy = (nextFragment as? AnimationScreen)?.screenAnimationStrategy
        if (nextFragment !is BottomTabsFragment) {
            when (animationStrategy) {
                SLIDE_HORIZONTAL -> fragmentTransaction.setHorizontalSlideAnimations()
                SLIDE_VERTICAL -> fragmentTransaction.setVerticalSlideAnimations()
                NONE, null -> fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE)
            }
        }
    }

    override fun back() {
        if (localStackCopy.size > 1) {
            fragmentManager.popBackStack()
            localStackCopy.removeAt(localStackCopy.lastIndex)
        } else {
            if (localStackCopy.isNotEmpty()) {
                fragmentManager.popBackStack()
                localStackCopy.removeAt(localStackCopy.lastIndex)
            }
            activityBack()
        }
    }
}
