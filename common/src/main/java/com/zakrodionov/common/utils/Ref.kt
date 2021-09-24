@file:Suppress("NOTHING_TO_INLINE")

package com.zakrodionov.common.utils

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import com.zakrodionov.common.extensions.isDebug
import timber.log.Timber

class Ref(var value: Int)

const val EnableDebugCompositionLogs = false

/**
 * An effect which logs the number compositions at the invoked point of the slot table.
 * Thanks to [objcode](https://github.com/objcode) for this code.
 *
 * This is an inline function to act as like a C-style #include to the host composable function.
 * That way we track it's compositions, not this function's compositions.
 *
 * @param tag Log tag used for [Log.d]
 */
@Composable
inline fun LogCompositions(tag: String) {
    if (EnableDebugCompositionLogs && isDebug) {
        val ref = remember { Ref(0) }
        SideEffect { ref.value++ }
        Timber.d("Compositions: " + ref.value)
    }
}
