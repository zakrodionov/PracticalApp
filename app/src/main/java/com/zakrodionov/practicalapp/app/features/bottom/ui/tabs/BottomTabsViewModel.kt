package com.zakrodionov.practicalapp.app.features.bottom.ui.tabs

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.zakrodionov.practicalapp.app.core.BaseViewModel
import com.zakrodionov.practicalapp.app.core.navigation.TabFlowRouter
import com.zakrodionov.practicalapp.app.features.bottom.base.Tab
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BottomTabsViewModel(
    savedStateHandle: SavedStateHandle,
    private val tabFlowRouter: TabFlowRouter,
) : BaseViewModel<BottomTabsState, BottomTabsEvent>(BottomTabsState(), savedStateHandle) {

    init {
        initTabs()

        observeSelectedTab()
    }

    private fun observeSelectedTab() {
        tabFlowRouter.selectedTab
            .onEach {
                reduce { state.copy(currentTab = it) }
            }
            .launchIn(viewModelScope)
    }

    private fun initTabs() {
        launch {
            // При смене экрана на BottomTabsFragment
            // наблюдается лаг в TabFlowNavigator -> newFragment -> add. Пока такой фикс(
            delay(200)
            switchTab(state.currentTab)
        }
    }

    fun switchTab(tab: Tab) {
        tabFlowRouter.switchTab(tab)
    }

    fun onBackTab() {
        tabFlowRouter.onBackTab()
    }
}
