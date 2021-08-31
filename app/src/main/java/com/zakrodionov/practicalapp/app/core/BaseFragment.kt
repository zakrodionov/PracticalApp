package com.zakrodionov.practicalapp.app.core

import android.os.Bundle
import android.os.Parcelable
import android.os.SystemClock
import android.view.View
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.core.view.doOnLayout
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.zakrodionov.common.core.asString
import com.zakrodionov.common.extensions.getCompatColor
import com.zakrodionov.common.extensions.hideKeyboard
import com.zakrodionov.common.extensions.repeatOnStarted
import com.zakrodionov.common.extensions.setStatusBarLightMode
import com.zakrodionov.common.extensions.showSnackbar
import com.zakrodionov.common.extensions.showToast
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.navigation.AnimationScreen
import com.zakrodionov.practicalapp.app.core.navigation.BackButtonListener
import com.zakrodionov.practicalapp.app.core.navigation.ScreenAnimationStrategy
import com.zakrodionov.practicalapp.app.core.navigation.ScreenAnimationStrategy.SLIDE_HORIZONTAL
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.component.KoinScopeComponent
import org.koin.core.scope.Scope

@Suppress("TooManyFunctions")
abstract class BaseFragment<STATE : Parcelable, SIDE_EFFECT : Any>(@LayoutRes contentLayoutId: Int) :
    BaseRealDestroyFragment(contentLayoutId), BackButtonListener, AnimationScreen, KoinScopeComponent {

    abstract val viewModel: BaseViewModel<STATE, SIDE_EFFECT>
    abstract val binding: ViewBinding

    open val statusBarColor = R.color.color_statusbar
    open val statusBarLightMode = false

    override val screenAnimationStrategy: ScreenAnimationStrategy = SLIDE_HORIZONTAL

    // По умолчанию Koin скоуп родительского флоу фргамента.
    // Можно переопрделелить на собственный getOrCreateFragmentScope(),
    // но и уничтожение скоупа тоже надо будет реализовать.
    override val scope: Scope
        get() = (parentFragment as BaseFlowFragment).scope

    private var snackBar: Snackbar? = null
    private var viewCreatedTime: Long = 0

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.statusBarColor = getCompatColor(statusBarColor)
        activity?.window?.setStatusBarLightMode(statusBarLightMode)

        applyInsets()

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

        // Fixme Когда возвращаешься с фрагмента с клавиатурой
        // даже при том что она начинает скрыватся, на предыдущем экране insets не обновятся
        view.doOnLayout {
            view.requestApplyInsets()
        }
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
                snackBar?.dismiss()
                snackBar = view?.showSnackbar(showEvent.message.asString(resources))
            }
        }
    }

    open fun applyInsets() {
        view?.applyInsetter {
            type(ime = true, statusBars = true) {
                margin()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        view?.hideKeyboard()
    }

    override fun onBackPressed(): Boolean = true

    open fun loadData() {}

    fun waitForAnimation(animationDelay: Long, block: () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            val delay =
                maxOf((animationDelay - (SystemClock.elapsedRealtime() - viewCreatedTime)), 0)
            delay(delay)
            block.invoke()
        }
    }
}
