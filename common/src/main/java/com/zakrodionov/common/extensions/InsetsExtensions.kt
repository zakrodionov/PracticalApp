package com.zakrodionov.common.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues

@Composable
fun edgeToEdgeStatusBarPadding(
    additionalHorizontal: Dp = 0.dp,
    additionalVertical: Dp = 0.dp,
) = rememberInsetsPaddingValues(
    insets = LocalWindowInsets.current.statusBars,
    applyBottom = false,
    additionalStart = additionalHorizontal,
    additionalTop = additionalVertical,
    additionalEnd = additionalHorizontal,
    additionalBottom = additionalVertical
)
