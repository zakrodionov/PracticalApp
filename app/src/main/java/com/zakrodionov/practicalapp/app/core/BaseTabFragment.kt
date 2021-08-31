package com.zakrodionov.practicalapp.app.core

import androidx.annotation.CallSuper

abstract class BaseTabFragment(
    contentLayoutId: Int,
    containerId: Int,
) : BaseFlowFragment(contentLayoutId, containerId) {

    open fun onTabSelected() {}

    @CallSuper
    open fun onTabReselected() {
        flowRouter.backTo(startScreen)
    }

    open fun onTabUnselected() {}
}
