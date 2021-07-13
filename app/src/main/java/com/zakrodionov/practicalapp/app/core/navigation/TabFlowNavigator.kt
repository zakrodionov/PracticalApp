package com.zakrodionov.practicalapp.app.core.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import androidx.lifecycle.Lifecycle
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.zakrodionov.practicalapp.app.core.BaseTabFragment
import com.zakrodionov.practicalapp.app.core.navigation.TransactionStrategy.ATTACH_DETACH
import com.zakrodionov.practicalapp.app.core.navigation.TransactionStrategy.SHOW_HIDE
import com.zakrodionov.practicalapp.app.features.bottom.base.Tab

enum class TransactionStrategy {
    SHOW_HIDE,
    ATTACH_DETACH // Default. Destroy view on switch tab
}

// Для навигации во флоу боттом экране
class TabFlowNavigator(
    activity: FragmentActivity,
    fragmentManager: FragmentManager,
    @IdRes containerId: Int,
    private val transactionStrategy: TransactionStrategy = ATTACH_DETACH,
) : AppNavigator(activity, containerId, fragmentManager) {

    private val currentTabFragment: BaseTabFragment?
        get() = fragmentManager.fragments.firstOrNull { !it.isHidden } as? BaseTabFragment

    override fun applyCommand(command: Command) {
        when (command) {
            is SwitchToTab -> {
                openTab(command.tab)
            }

            is ResetTab -> {
                fragmentManager.fragments.forEach {
                    if (Tab.from(it) == command.tab) {
                        fragmentManager.commit { remove(it) }
                    }
                }
            }

            is ResetAllTabs -> {
                clearFragments()
            }

            is ReselectTab -> {
                currentTabFragment?.onTabReselected()
            }

            else -> super.applyCommand(command)
        }
    }

    private fun openTab(tab: Tab) {
        val currentFragment = currentTabFragment
        val newFragment = fragmentManager.findFragmentByTag(tab.name)

        if (currentFragment != null && newFragment != null && currentFragment == newFragment) {
            return
        }

        fragmentManager.commitNow {
            if (newFragment == null) {
                add(containerId, tab.getFragment(), tab.name)
            }

            currentFragment?.let {
                when (transactionStrategy) {
                    SHOW_HIDE -> hide(it)
                    ATTACH_DETACH -> detach(it)
                }
                setMaxLifecycle(it, Lifecycle.State.STARTED)
                it.onTabUnselected()
            }

            newFragment?.let {
                when (transactionStrategy) {
                    SHOW_HIDE -> show(it)
                    ATTACH_DETACH -> attach(it)
                }
                setMaxLifecycle(it, Lifecycle.State.RESUMED)
                (it as? BaseTabFragment)?.onTabSelected()
            }
        }
    }

    private fun clearFragments() {
        fragmentManager.commitNow { fragmentManager.fragments.forEach { remove(it) } }
    }
}
