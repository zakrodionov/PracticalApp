package com.zakrodionov.practicalapp.app.core

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.zakrodionov.common.dialogs.CommonDialog.Companion.TAG_COMMON_DIALOG
import com.zakrodionov.common.extensions.getCompatColor
import com.zakrodionov.common.extensions.hideKeyboard
import com.zakrodionov.common.extensions.setStatusBarColor
import com.zakrodionov.common.extensions.setStatusBarLightMode
import com.zakrodionov.common.extensions.showDialog
import com.zakrodionov.common.extensions.showSnackbar
import com.zakrodionov.common.extensions.showToast
import com.zakrodionov.common.ui.ShowAction
import com.zakrodionov.practicalapp.R
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.getKoin
import org.koin.android.scope.AndroidScopeComponent
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.scope.Scope

abstract class BaseFragment<STATE : Any, SIDE_EFFECT : Any>(@LayoutRes contentLayoutId: Int) :
    Fragment(contentLayoutId), AndroidScopeComponent {

    @OptIn(KoinInternalApi::class)
    override val scope: Scope
        get() = (parentFragment as? AndroidScopeComponent)?.scope ?: getKoin().getRootScope()

    abstract val viewModel: BaseViewModel<STATE, SIDE_EFFECT>
    abstract val binding: ViewBinding

    open val statusBarColor = R.color.color_statusbar
    open val statusBarLightMode = false

    private var snackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.setStatusBarColor(requireContext().getCompatColor(statusBarColor))
        activity?.setStatusBarLightMode(statusBarLightMode)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.container.sideEffectFlow.collect { sideEffect(it) }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.container.stateFlow.collect { render(it) }
        }
    }

    open fun setupViews(view: View, savedInstanceState: Bundle?) = Unit

    abstract fun sideEffect(event: SIDE_EFFECT)

    abstract fun render(state: STATE)

    open fun handleShowAction(showAction: ShowAction) {
        when (showAction) {
            is ShowAction.ShowDialog -> {
                showDialog(
                    message = showAction.message.getText(this),
                    fragmentManager = childFragmentManager,
                    tag = TAG_COMMON_DIALOG
                )
            }
            is ShowAction.ShowToast -> {
                showToast(showAction.message.getText(this), Toast.LENGTH_LONG)
            }
            is ShowAction.ShowSnackbar -> {
                view?.let {
                    snackBar?.dismiss()
                    snackBar = showSnackbar(it, showAction.message.getText(this))
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        view?.hideKeyboard()
    }

    open fun loadData() {}
}