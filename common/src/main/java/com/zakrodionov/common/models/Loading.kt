package com.zakrodionov.common.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
// Небольшой класс для помощи обработки загрузки с SwipeRefreshLayout
data class Loading(
    val isLoading: Boolean = false,
    val fromSwipeRefresh: Boolean = false,
) : Parcelable
