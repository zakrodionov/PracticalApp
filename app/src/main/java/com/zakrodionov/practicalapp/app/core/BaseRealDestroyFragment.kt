package com.zakrodionov.practicalapp.app.core

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes

// Fragment with correct onDestroy () call
// Which is not called when the fragment or app process is recreated
// Checking to destroy a fragment from Moxy
// https://github.com/moxy-community/Moxy/blob/develop/moxy-androidx/src/main/java/moxy/MvpAppCompatFragment.java
abstract class BaseRealDestroyFragment(@LayoutRes contentLayoutId: Int) : BaseUniqueIdFragment(contentLayoutId) {

    private var instanceStateSaved: Boolean = false

    @CallSuper
    override fun onStart() {
        super.onStart()
        instanceStateSaved = false
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        instanceStateSaved = false
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
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

    open fun onRealDestroy() = Unit
}
