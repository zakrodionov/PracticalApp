package com.zakrodionov.practicalapp.app.core

import android.content.Context
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.github.terrakok.modo.android.AppScreen
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.navigation.FlowModoRouter
import com.zakrodionov.practicalapp.app.core.navigation.ModoRenderWithAnimation
import org.koin.android.scope.AndroidScopeComponent

abstract class BaseFlowFragment(@LayoutRes contentLayoutId: Int = R.layout.layout_container) :
    Fragment(contentLayoutId), AndroidScopeComponent {

    abstract val loadModule: Unit
    abstract val flowModoRouter: FlowModoRouter

    abstract fun getFirstScreen(): AppScreen

    open val modoRender by lazy {
        ModoRenderWithAnimation(childFragmentManager, R.id.fragmentContainerView) { flowModoRouter.finishFlow() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                flowModoRouter.back()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        flowModoRouter.init(savedInstanceState, getFirstScreen())
    }

    override fun onResume() {
        super.onResume()
        flowModoRouter.render(modoRender)
    }

    override fun onPause() {
        flowModoRouter.render(null)
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        flowModoRouter.saveState(outState)
    }
}
