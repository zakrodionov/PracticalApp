package com.zakrodionov.practicalapp.app.core.navigation

import android.os.Bundle
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Router
import com.zakrodionov.common.extensions.debug
import com.zakrodionov.practicalapp.app.core.InstanceStateSaver
import com.zakrodionov.practicalapp.app.core.navigation.BackTabStrategy.BACK_TO_DEFAULT_TAB
import com.zakrodionov.practicalapp.app.core.navigation.BackTabStrategy.BACK_TO_FIRST_TAB
import com.zakrodionov.practicalapp.app.core.navigation.BackTabStrategy.BY_SHOW_ORDER
import com.zakrodionov.practicalapp.app.core.navigation.BackTabStrategy.NONE
import com.zakrodionov.practicalapp.app.features.bottom.base.Tab
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

data class SwitchToTab(val tab: Tab) : Command
data class ResetTab(val tab: Tab) : Command
object ResetAllTabs : Command
object ReselectTab : Command

enum class BackTabStrategy {
    BACK_TO_FIRST_TAB,
    BACK_TO_DEFAULT_TAB,
    BY_SHOW_ORDER,
    NONE
}

interface TabsRouter {

    val selectedTab: Flow<Tab>

    fun switchTab(tab: Tab)

    fun reloadTab(tab: Tab)

    fun resetTab(tab: Tab)

    fun resetAllTabsAndReopenCurrentTab()

    fun resetAllTabsAndOpenNewTab(tab: Tab)

    fun resetAllTabs()

    fun onBackTab()
}

// Для навигации во флоу боттом экране
@Suppress("TooManyFunctions")
class TabFlowRouter(private val backTabStrategy: BackTabStrategy = BY_SHOW_ORDER) :
    Router(),
    TabsRouter,
    InstanceStateSaver {

    companion object {
        const val KEY_TABS_HISTORY = "KEY_TABS_HISTORY"
        const val KEY_SELECTED_TAB = "KEY_SELECTED_TAB"
    }

    private val firstTab = Tab.values().first()
    private val tabsHistory = mutableListOf<Tab>()

    private val _selectedTab = MutableStateFlow<Tab?>(null)
    override val selectedTab: Flow<Tab> = _selectedTab.filterNotNull()

    override fun switchTab(tab: Tab) {
        if (_selectedTab.value == tab) {
            executeCommands(ReselectTab)
            return
        }
        executeCommands(SwitchToTab(tab))

        innerSelectTab(tab)
    }

    override fun reloadTab(tab: Tab) {
        executeCommands(
            ResetTab(tab),
            SwitchToTab(tab)
        )
    }

    @Suppress("SpreadOperator")
    override fun resetTab(tab: Tab) {
        val commands = arrayListOf<Command>(ResetTab(tab))
        if (_selectedTab.value == tab) {
            commands.add(SwitchToTab(tab))
        }
        executeCommands(*commands.toTypedArray())
    }

    override fun resetAllTabsAndReopenCurrentTab() {
        resetAllTabsAndOpenNewTab(_selectedTab.value ?: Tab.DEFAULT_TAB)
    }

    override fun resetAllTabsAndOpenNewTab(tab: Tab) {
        executeCommands(
            ResetAllTabs,
            SwitchToTab(tab)
        )

        innerSelectTab(tab)
    }

    override fun resetAllTabs() {
        executeCommands(ResetAllTabs)
        _selectedTab.value = null

        clearHistory()
    }

    private fun innerSelectTab(tab: Tab) {
        _selectedTab.value = tab

        addTabToHistory(tab)
    }

    private fun addTabToHistory(tab: Tab) {
        if (tabsHistory.contains(tab)) {
            tabsHistory.remove(tab)
        }
        tabsHistory.add(tab)
    }

    private fun clearHistory() {
        tabsHistory.clear()
    }

    override fun onBackTab() {
        when (backTabStrategy) {
            BACK_TO_FIRST_TAB -> {
                if (selectedTab() == firstTab) {
                    closeTabFlow()
                } else {
                    switchTab(firstTab)
                }
            }
            BACK_TO_DEFAULT_TAB -> {
                if (selectedTab() == Tab.DEFAULT_TAB) {
                    closeTabFlow()
                } else {
                    switchTab(Tab.DEFAULT_TAB)
                }
            }
            BY_SHOW_ORDER -> {
                if (tabsHistory.size < 2) {
                    closeTabFlow()
                } else {
                    val lastTab = tabsHistory.run {
                        removeLast()
                        last()
                    }
                    switchTab(lastTab)
                }
            }
            NONE -> {
                closeTabFlow()
            }
        }
    }

    private fun selectedTab(): Tab? = _selectedTab.value

    private fun closeTabFlow() {
        finishChain()
        _selectedTab.value = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(KEY_TABS_HISTORY, arrayListOf(*tabsHistory.toTypedArray()))
        outState.putParcelable(KEY_SELECTED_TAB, _selectedTab.value)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        val savedHistory = savedInstanceState?.getParcelableArrayList<Tab>(KEY_TABS_HISTORY)
        debug("$savedHistory")
        if (savedHistory != null && savedHistory.isNotEmpty()) {
            tabsHistory.clear()
            tabsHistory.addAll(savedHistory)
        }

        val savedSelectedTab = savedInstanceState?.getParcelable<Tab>(KEY_SELECTED_TAB)
        debug("$savedSelectedTab")
        if (savedSelectedTab != null) {
            _selectedTab.value = savedSelectedTab
        }
    }
}
