package com.zakrodionov.common.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope

// Выполняется только при первой композиции. Предоставляет CoroutineScope.
@Composable
fun OnLaunched(block: suspend CoroutineScope.() -> Unit) = LaunchedEffect(key1 = Unit, block = block)

// Выполняется только при первой композиции.
@Composable
fun OnLaunched(block: () -> Unit, onDispose: () -> Unit = {}) = DisposableEffect(Unit) {
    block()
    onDispose { onDispose() }
}

