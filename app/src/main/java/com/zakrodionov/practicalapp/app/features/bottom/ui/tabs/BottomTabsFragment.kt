package com.zakrodionov.practicalapp.app.features.bottom.ui.tabs

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.terrakok.cicerone.NavigatorHolder
import com.google.android.material.navigation.NavigationBarView
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseFragment
import com.zakrodionov.practicalapp.app.core.navigation.TabFlowNavigator
import com.zakrodionov.practicalapp.app.core.navigation.TabFlowRouter
import com.zakrodionov.practicalapp.app.core.navigation.TabHost
import com.zakrodionov.practicalapp.app.di.getOrCreateFragmentScope
import com.zakrodionov.practicalapp.app.features.bottom.base.Tab
import com.zakrodionov.practicalapp.databinding.FragmentBottomTabsBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.core.scope.Scope

class BottomTabsFragment :
    BaseFragment<BottomTabsState, BottomTabsEvent>(R.layout.fragment_bottom_tabs), TabHost {

    companion object {
        const val ARG_INITIAL_SCREEN = "ARG_INITIAL_SCREEN"
        const val KEY_INITIAL_SCREEN_HANDLED = "KEY_INITIAL_SCREEN_HANDLED"

        fun newInstance(initialScreen: String) = BottomTabsFragment().apply {
            arguments = bundleOf(ARG_INITIAL_SCREEN to initialScreen)
        }
    }

    override val scope: Scope by lazy {
        getOrCreateFragmentScope(uniqueId)
    }

    override val viewModel: BottomTabsViewModel by stateViewModel()
    override val binding by viewBinding(FragmentBottomTabsBinding::bind)
    override val statusBarColor: Int = R.color.transparent

    private val tabFlowRouter: TabFlowRouter by inject()
    private val navigatorHolder: NavigatorHolder by inject()
    private val navigator: TabFlowNavigator by lazy {
        TabFlowNavigator(requireActivity(), childFragmentManager, R.id.bottomFragmentContainerView)
    }

    private val bottomNavigationItemSelectedListener =
        NavigationBarView.OnItemSelectedListener { item ->
            viewModel.switchTab(Tab.from(item))
            true
        }

    private var initialScreenHandled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabFlowRouter.onRestoreInstanceState(savedInstanceState)

        // TODO deeplink test
        val initialScreen = arguments?.getString(ARG_INITIAL_SCREEN)
        initialScreenHandled = savedInstanceState?.getBoolean(KEY_INITIAL_SCREEN_HANDLED) ?: false
        if (!initialScreen.isNullOrEmpty() && savedInstanceState == null && !initialScreenHandled) {
            when (initialScreen) {
                "favorite" -> viewModel.switchTab(Tab.FAVORITE)
            }
        }
        initialScreenHandled = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener(bottomNavigationItemSelectedListener)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    // Игнорируем инсеты для экрана с BottomNavigationView
    override fun applyInsets() = Unit

    override fun render(state: BottomTabsState) = with(binding) {
        bottomNavigation.setOnItemSelectedListener(null)
        bottomNavigation.selectedItemId = state.currentTab.menuItemId
        bottomNavigation.setOnItemSelectedListener(bottomNavigationItemSelectedListener)
    }

    override fun onBackTab() {
        viewModel.onBackTab()
    }

    override fun sideEffect(event: BottomTabsEvent) = Unit

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        tabFlowRouter.onSaveInstanceState(outState)
        outState.putBoolean(KEY_INITIAL_SCREEN_HANDLED, initialScreenHandled)
    }

    override fun onRealDestroy() {
        closeScope()
    }
}
