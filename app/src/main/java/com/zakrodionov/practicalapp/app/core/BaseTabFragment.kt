package com.zakrodionov.practicalapp.app.core

import androidx.annotation.CallSuper

abstract class BaseTabFragment(
    contentLayoutId: Int,
    containerId: Int,
    qualifier: String
) : BaseFlowFragment(contentLayoutId, containerId, qualifier) {

    open fun onTabSelected() {}

    @CallSuper
    open fun onTabReselected() {
        flowRouter.backTo(startScreen)
    }

    open fun onTabUnselected() {}
}
