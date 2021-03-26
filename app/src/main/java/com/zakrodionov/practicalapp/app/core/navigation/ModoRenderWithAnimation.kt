package com.zakrodionov.practicalapp.app.core.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.modo.android.AppScreen
import com.github.terrakok.modo.android.ModoRender
import com.zakrodionov.common.extensions.setDefaultAnimations

open class ModoRenderWithAnimation(
    fragmentManager: FragmentManager,
    containerId: Int,
    exitAction: () -> Unit
) : ModoRender(fragmentManager, containerId, exitAction) {

    constructor(
        activity: FragmentActivity,
        containerId: Int
    ) : this(activity.supportFragmentManager, containerId, { activity.finish() })

    override fun setupTransaction(
        fragmentManager: FragmentManager,
        transaction: FragmentTransaction,
        screen: AppScreen,
        newFragment: Fragment
    ) {
        transaction.setDefaultAnimations()
    }
}
