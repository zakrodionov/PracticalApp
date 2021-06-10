package com.zakrodionov.practicalapp.app.core

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Screen
import com.zakrodionov.common.extensions.getCurrentFragment
import com.zakrodionov.practicalapp.app.core.navigation.AnimationScreen
import com.zakrodionov.practicalapp.app.core.navigation.BackButtonListener
import com.zakrodionov.practicalapp.app.core.navigation.BackTabStrategy
import com.zakrodionov.practicalapp.app.core.navigation.BaseNavigator
import com.zakrodionov.practicalapp.app.core.navigation.FlowRouter
import com.zakrodionov.practicalapp.app.core.navigation.ScreenAnimationStrategy
import com.zakrodionov.practicalapp.app.core.navigation.ScreenAnimationStrategy.SLIDE_HORIZONTAL
import com.zakrodionov.practicalapp.app.core.navigation.TabHost
import com.zakrodionov.practicalapp.app.core.navigation.toRouterQualifier
import com.zakrodionov.practicalapp.app.di.DIQualifiers.navigationHolderQualifier
import org.koin.android.ext.android.inject

abstract class BaseFlowFragment(
    @LayoutRes contentLayoutId: Int,
    @IdRes private val containerId: Int,
    qualifier: String
) : Fragment(contentLayoutId), AnimationScreen {

    abstract val startScreen: Screen

    override val screenAnimationStrategy: ScreenAnimationStrategy = SLIDE_HORIZONTAL

    protected val flowRouter: FlowRouter by inject(qualifier.toRouterQualifier)

    private val navigator by lazy {
        createNavigator()
    }

    private val navigatorHolder: NavigatorHolder by inject(navigationHolderQualifier(qualifier))

    protected open fun createNavigator() =
        object : BaseNavigator(requireActivity(), childFragmentManager, containerId) {
            override fun activityBack() {
                onBackPressedCallback.handleOnBackPressed()
            }
        }

    protected val currentFragment: Fragment?
        get() = getCurrentFragment()

    /**
     * В кратце шо тут происходит:
     * 1. Если мы достигли начального экрана во флоу и в активити больше нет других фрагментов, мы
     * либо переключаем таб согласно [BackTabStrategy], или закрываем приложение
     * 2. Если мы достигли начального экрана во флоу, НО в активити еще есть другие экраны - закрываем флоу
     * 3. Просто переключаем назад экраны по флоу
     * */
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val fragments = childFragmentManager.fragments
            val needBack = (fragments.lastOrNull() as? BackButtonListener)?.onBackPressed() ?: true
            if (needBack) {
                when {
                    childFragmentManager.backStackEntryCount <= 1 &&
                        requireActivity().supportFragmentManager.backStackEntryCount < 1 -> {

                        if (parentFragment is TabHost) {
                            (parentFragment as TabHost).onBackTab()
                        } else {
                            flowRouter.finishApp()
                        }
                    }
                    childFragmentManager.backStackEntryCount <= 1 -> {
                        flowRouter.finishFlow()
                    }
                    else -> {
                        flowRouter.exit()
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            flowRouter.navigateTo(startScreen)
        }
    }

    // Add onBackPressedDispatcher in onViewCreated, it's important
    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    override fun onResume() {
        super.onResume()
        onBackPressedCallback.isEnabled = true
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        onBackPressedCallback.isEnabled = false
        navigatorHolder.removeNavigator()
    }
}
