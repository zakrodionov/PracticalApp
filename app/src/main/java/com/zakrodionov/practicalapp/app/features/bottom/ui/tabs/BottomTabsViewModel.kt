package com.zakrodionov.practicalapp.app.features.bottom.ui.tabs

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.zakrodionov.practicalapp.app.core.BaseViewModel
import com.zakrodionov.practicalapp.app.core.navigation.GlobalRouter
import com.zakrodionov.practicalapp.app.core.navigation.TabFlowRouter
import com.zakrodionov.practicalapp.app.features.bottom.base.BottomTabsEventProvider
import com.zakrodionov.practicalapp.app.features.bottom.base.SwitchTabToFavoriteEvent
import com.zakrodionov.practicalapp.app.features.bottom.base.Tab
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArgsBottomTabs(val selectedTab: Tab = Tab.POSTS) : Parcelable

class BottomTabsViewModel(
    savedStateHandle: SavedStateHandle,
    private val argsBottomTabs: ArgsBottomTabs,
    private val tabFlowRouter: TabFlowRouter,
    private val globalRouter: GlobalRouter,
    private val bottomTabsEventProvider: BottomTabsEventProvider,
) : BaseViewModel<BottomTabsState, BottomTabsEvent>(BottomTabsState(currentTab = argsBottomTabs.selectedTab),
    savedStateHandle) {

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

        bottomTabsEventProvider
            .provide()
            .filterIsInstance<SwitchTabToFavoriteEvent>()
            .onEach {
                bottomTabsEventProvider.handleEvent()
                switchTab(Tab.FAVORITE)
            }
            .launchIn(viewModelScope)
    }

    private fun initTabs() {
        switchTab(Tab.POSTS) // Always preload first tab
        switchTab(state.currentTab)
    }

    fun switchTab(tab: Tab) {
        tabFlowRouter.switchTab(tab)
    }

    fun onBackTab() {
        tabFlowRouter.onBackTab()
    }
}
