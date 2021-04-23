package com.zakrodionov.practicalapp.app.core.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.zakrodionov.common.extensions.setDefaultAnimations
import com.zakrodionov.practicalapp.app.core.navigation.ScreenAnimationStrategy.NONE
import com.zakrodionov.practicalapp.app.core.navigation.ScreenAnimationStrategy.SLIDE_HORIZONTAL
import com.zakrodionov.practicalapp.app.core.navigation.ScreenAnimationStrategy.SLIDE_VERTICAL

// Обычный навигатор между экранами
open class BaseNavigator(activity: FragmentActivity, fragmentManager: FragmentManager, @IdRes containerId: Int) :
    AppNavigator(activity, containerId, fragmentManager) {
    override fun setupFragmentTransaction(
        screen: FragmentScreen,
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment
    ) {
        val animationStrategy = (nextFragment as? AnimationScreen)?.screenAnimationStrategy
        if (currentFragment != null) {
            when (animationStrategy) {
                SLIDE_HORIZONTAL -> fragmentTransaction.setDefaultAnimations()
                SLIDE_VERTICAL -> {
                    // TODO
                }
                NONE, null -> fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE)
            }
        }
    }
}
