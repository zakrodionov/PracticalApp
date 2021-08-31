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
import com.zakrodionov.practicalapp.app.di.getOrCreateFragmentScope
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinScopeComponent
import org.koin.core.scope.Scope
import java.util.*
import kotlin.properties.Delegates

abstract class BaseFlowFragment(
    @LayoutRes contentLayoutId: Int,
    @IdRes private val containerId: Int,
) : Fragment(contentLayoutId), AnimationScreen, KoinScopeComponent {

    abstract val startScreen: Screen

    override val screenAnimationStrategy: ScreenAnimationStrategy = SLIDE_HORIZONTAL

    override val scope: Scope by lazy {
        getOrCreateFragmentScope(uniqueId)
    }

    protected val flowRouter: FlowRouter by inject()

    private val navigator by lazy { createNavigator() }

    private val navigatorHolder: NavigatorHolder by inject()

    protected open fun createNavigator() =
        object : BaseNavigator(requireActivity(), childFragmentManager, containerId) {
            override fun activityBack() {
                onBackPressedCallback.handleOnBackPressed()
            }
        }

    protected val currentFragment: Fragment?
        get() = getCurrentFragment()

    private var instanceStateSaved: Boolean = false
    protected var uniqueId: UUID by Delegates.notNull()

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
        uniqueId = savedInstanceState?.getSerializable(KEY_UNIQUE_ID) as? UUID ?: UUID.randomUUID()
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

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_UNIQUE_ID, uniqueId)
        instanceStateSaved = true
    }

    // Checking to destroy a fragment from
    // https://github.com/moxy-community/Moxy/blob/develop/moxy-androidx/src/main/java/moxy/MvpAppCompatFragment.java
    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        // We leave the screen and respectively all fragments will be destroyed
        if (requireActivity().isFinishing) {
            onRealDestroy()
            return
        }

        // When we rotate device isRemoving() return true for fragment placed in backstack
        // http://stackoverflow.com/questions/34649126/fragment-back-stack-and-isremoving
        if (instanceStateSaved) {
            instanceStateSaved = false
            return
        }

        var anyParentIsRemoving = false
        var parent = parentFragment
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving
            parent = parent.parentFragment
        }

        if (isRemoving || anyParentIsRemoving) {
            onRealDestroy()
        }
    }

    @CallSuper
    open fun onRealDestroy() {
        closeScope()
    }
}
