package com.zakrodionov.practicalapp.app.features.bottom.ui.tabs

import androidx.lifecycle.SavedStateHandle
import com.zakrodionov.practicalapp.app.core.BaseError
import com.zakrodionov.practicalapp.app.core.BaseViewModel
import com.zakrodionov.practicalapp.app.core.navigation.TabFlowRouter
import com.zakrodionov.practicalapp.app.features.bottom.base.Tab
import kotlinx.coroutines.flow.collect

class BottomTabsViewModel(
    savedStateHandle: SavedStateHandle,
    private val tabFlowRouter: TabFlowRouter
) : BaseViewModel<BottomTabsState, BottomTabsEvent>(BottomTabsState(), savedStateHandle) {

    init {
        observeSelectedTab()

        initTabs()
    }

    private fun observeSelectedTab() {
        launch {
            tabFlowRouter.selectedTabFlow.collect {
                reduce { state.copy(currentTab = it) }
            }
        }
    }

    private fun initTabs() {
        launch {
            if (!state.tabsIsInitialized) {
                tabFlowRouter.resetAllTabsAndOpenNewTab(Tab.POSTS)
                reduce { state.copy(tabsIsInitialized = true) }
            }
        }
    }

    fun switchTab(tab: Tab) {
        launch {
            tabFlowRouter.switchTab(tab)
        }
    }

    fun onBackTab() {
        tabFlowRouter.onBackTab()
    }

    override suspend fun handleError(baseError: BaseError) = Unit
}
