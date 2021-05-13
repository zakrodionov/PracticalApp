package com.zakrodionov.common.ui.lce

import com.zakrodionov.common.core.TextResource

data class UiError(
    val title: TextResource,
    val message: TextResource,
    val isNetworkError: Boolean
)
