package com.zakrodionov.practicalapp.app.features.bottom.ui.tabs

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.terrakok.cicerone.NavigatorHolder
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseFragment
import com.zakrodionov.practicalapp.app.core.navigation.TabFlowNavigator
import com.zakrodionov.practicalapp.app.core.navigation.TabHost
import com.zakrodionov.practicalapp.app.di.DIQualifiers.navigationHolderQualifier
import com.zakrodionov.practicalapp.app.features.bottom.base.Tab
import com.zakrodionov.practicalapp.app.features.bottom.di.BOTTOM_TABS_QUALIFIER
import com.zakrodionov.practicalapp.app.features.bottom.di.bottomTabsModule
import com.zakrodionov.practicalapp.databinding.FragmentBottomTabsBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class BottomTabsFragment : BaseFragment<BottomTabsState, BottomTabsEvent>(R.layout.fragment_bottom_tabs), TabHost {

    companion object {
        fun newInstance() = BottomTabsFragment()
    }

    override val viewModel: BottomTabsViewModel by stateViewModel()
    override val binding by viewBinding(FragmentBottomTabsBinding::bind)

    private val navigatorHolder: NavigatorHolder by inject(navigationHolderQualifier(BOTTOM_TABS_QUALIFIER))
    private val navigator: TabFlowNavigator by lazy {
        TabFlowNavigator(requireActivity(), childFragmentManager, R.id.bottomFragmentContainerView)
    }

    private val bottomNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        viewModel.switchTab(Tab.from(item))
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(bottomTabsModule)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(bottomNavigationItemSelectedListener)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onRealDestroy() {
        unloadKoinModules(bottomTabsModule)
    }

    override fun render(state: BottomTabsState) {
        state.currentTab?.let {
            binding.bottomNavigation.setOnNavigationItemSelectedListener(null)
            binding.bottomNavigation.selectedItemId = it.menuItemId
            binding.bottomNavigation.setOnNavigationItemSelectedListener(bottomNavigationItemSelectedListener)
        }
    }

    override fun onBackTab() {
        viewModel.onBackTab()
    }

    override fun sideEffect(event: BottomTabsEvent) = Unit
}
