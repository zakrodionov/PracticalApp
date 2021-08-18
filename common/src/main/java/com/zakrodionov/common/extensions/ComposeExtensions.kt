package com.zakrodionov.common.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope

// Поз
@Composable
fun Subscribe(block: suspend CoroutineScope.() -> Unit) = LaunchedEffect(key1 = Unit, block = block)
