package com.zakrodionov.practicalapp.app.core

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.zakrodionov.common.core.asString
import com.zakrodionov.common.dialogs.FixedBottomSheetDialogFragment
import com.zakrodionov.common.extensions.getCompatColor
import com.zakrodionov.common.extensions.hideKeyboard
import com.zakrodionov.common.extensions.repeatOnStarted
import com.zakrodionov.common.extensions.setStatusBarLightMode
import com.zakrodionov.common.extensions.showSnackbar
import com.zakrodionov.common.extensions.showToast
import com.zakrodionov.practicalapp.R
import kotlinx.coroutines.flow.collect

@Suppress("TooManyFunctions")
abstract class BaseBottomSheetDialogFragment<STATE : Parcelable, SIDE_EFFECT : Any>(
    @LayoutRes contentLayoutId: Int
) : FixedBottomSheetDialogFragment(contentLayoutId) {

    abstract val viewModel: BaseViewModel<STATE, SIDE_EFFECT>
    abstract val binding: ViewBinding

    open val statusBarColor = R.color.color_statusbar
    open val statusBarLightMode = false

    private var snackBar: Snackbar? = null

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.statusBarColor = requireContext().getCompatColor(statusBarColor)
        activity?.window?.setStatusBarLightMode(statusBarLightMode)

        setupViews(view, savedInstanceState)

        viewLifecycleOwner.repeatOnStarted {
            viewModel.stateFlow.collect { render(it) }
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.eventFlow.collect { sideEffect(it) }
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.showEventFlow.collect { showEvent(it) }
        }
    }

    override fun onDestroyView() {
        clearViews()
        super.onDestroyView()
    }

    // Метод для инициализации вью
    open fun setupViews(view: View, savedInstanceState: Bundle?) = Unit

    // Метод для очищения колбэков, ресурсов связанных с вью
    open fun clearViews() = Unit

    // Обрабатываем state от viewModel
    abstract fun render(state: STATE)

    // Обрабатываем sideEffect`ы от viewModel
    abstract fun sideEffect(event: SIDE_EFFECT)

    // Обрабатываем showEvent от viewModel
    open fun showEvent(showEvent: BaseShowEvent) {
        handleBaseShowEvent(showEvent)
    }

    // Используется для отображения базовых диалогов, снекбаров, тоастов
    open fun handleBaseShowEvent(showEvent: BaseShowEvent) {
        when (showEvent) {
            is ShowDialog -> {
                childFragmentManager.showDialog(showEvent)
            }
            is ShowToast -> {
                showToast(showEvent.message.asString(resources), Toast.LENGTH_LONG)
            }
            is ShowSnackbar -> {
                view?.let {
                    snackBar?.dismiss()
                    snackBar = showSnackbar(it, showEvent.message.asString(resources))
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
