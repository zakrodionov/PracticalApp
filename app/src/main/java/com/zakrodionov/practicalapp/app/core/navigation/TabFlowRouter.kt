package com.zakrodionov.practicalapp.app.core.navigation

import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Router
import com.zakrodionov.common.extensions.debug
import com.zakrodionov.practicalapp.app.core.navigation.BackTabStrategy.BACK_TO_DEFAULT_TAB
import com.zakrodionov.practicalapp.app.core.navigation.BackTabStrategy.BACK_TO_FIRST_TAB
import com.zakrodionov.practicalapp.app.core.navigation.BackTabStrategy.BY_SHOW_ORDER
import com.zakrodionov.practicalapp.app.core.navigation.BackTabStrategy.NONE
import com.zakrodionov.practicalapp.app.features.bottom.base.Tab
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach

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
class TabFlowRouter(private val backTabStrategy: BackTabStrategy = BY_SHOW_ORDER) : Router(), TabsRouter {

    private val firstTab = Tab.values().first()
    private val tabsHistory = mutableListOf<Tab>()

    private val _selectedTab = MutableStateFlow<Tab?>(null)
    override val selectedTab: Flow<Tab> = _selectedTab.filterNotNull().onEach {
        if (tabsHistory.contains(it)) {
            tabsHistory.remove(it)
        }
        tabsHistory.add(it)
    }.onEach {
        debug("router - $it")
    }

    override fun switchTab(tab: Tab) {
        if (_selectedTab.value == tab) {
            executeCommands(ReselectTab)
            return
        }
        executeCommands(SwitchToTab(tab))
        _selectedTab.value = tab
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
        _selectedTab.value = tab
    }

    override fun resetAllTabs() {
        executeCommands(ResetAllTabs)
        _selectedTab.value = null
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
}
