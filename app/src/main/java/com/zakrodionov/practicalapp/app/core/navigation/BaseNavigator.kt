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

// Обычный навигатор между экранами

open class BaseAnimatedNavigator(
    activity: FragmentActivity,
    fragmentManager: FragmentManager,
    @IdRes containerId: Int,
) : AppNavigator(activity, containerId, fragmentManager) {

    override fun setupFragmentTransaction(
        screen: FragmentScreen,
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment,
    ) {
        val animationStrategy = (nextFragment as? AnimationScreen)?.screenAnimationStrategy
        when (animationStrategy) {
            SLIDE_HORIZONTAL -> fragmentTransaction.setHorizontalSlideAnimations()
            SLIDE_VERTICAL -> fragmentTransaction.setVerticalSlideAnimations()
            NONE, null -> fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE)
        }
    }
}

// Навигатор для BaseFlowFragment. Для RootActivity достаточно BaseAnimatedNavigator.
open class BaseFlowNavigator(
    activity: FragmentActivity,
    fragmentManager: FragmentManager,
    @IdRes containerId: Int,
) : BaseAnimatedNavigator(activity, fragmentManager, containerId) {

    // Переопределяем back() для того чтобы при клике назад, когда остался один дочерний(рутовый) экран закрываем флоу
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
