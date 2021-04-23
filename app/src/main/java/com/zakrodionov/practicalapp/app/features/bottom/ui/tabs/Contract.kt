package com.zakrodionov.practicalapp.app.features.bottom.ui.tabs

import android.os.Parcelable
import com.zakrodionov.practicalapp.app.features.bottom.base.Tab
import kotlinx.parcelize.Parcelize

@Parcelize
data class BottomTabsState(
    val currentTab: Tab? = null,
    val tabsIsInitialized: Boolean = false
) : Parcelable

sealed class BottomTabsEvent
