package com.zakrodionov.practicalapp.app.features.bottom.ui.tabs

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.zakrodionov.practicalapp.app.core.BaseViewModel
import com.zakrodionov.practicalapp.app.core.navigation.TabFlowRouter
import com.zakrodionov.practicalapp.app.features.bottom.base.Tab
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BottomTabsViewModel(
    savedStateHandle: SavedStateHandle,
    private val tabFlowRouter: TabFlowRouter
) : BaseViewModel<BottomTabsState, BottomTabsEvent>(BottomTabsState(), savedStateHandle) {

    init {
        observeSelectedTab()

        initTabs()
    }

    private fun observeSelectedTab() {
        tabFlowRouter.selectedTab
            .onEach {
                reduce { state.copy(currentTab = it) }
            }
            .launchIn(viewModelScope)
    }

    private fun initTabs() {
        if (!state.tabsIsInitialized) {
            tabFlowRouter.resetAllTabsAndOpenNewTab(Tab.POSTS)
            reduce { state.copy(tabsIsInitialized = true) }
        }
    }

    fun switchTab(tab: Tab) {
        tabFlowRouter.switchTab(tab)
    }

    fun onBackTab() {
        tabFlowRouter.onBackTab()
    }
}
