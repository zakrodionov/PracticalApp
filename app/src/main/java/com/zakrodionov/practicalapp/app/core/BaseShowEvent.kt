package com.zakrodionov.practicalapp.app.core

import android.os.Parcelable
import androidx.annotation.StyleRes
import com.zakrodionov.common.core.TextResource

// Common show event
sealed class BaseShowEvent

data class ShowToast(val message: TextResource) : BaseShowEvent()
data class ShowSnackbar(val message: TextResource, val payload: Parcelable? = null) : BaseShowEvent()
data class ShowDialog(
    val title: TextResource? = null,
    val message: TextResource? = null,
    val btnPositiveText: TextResource? = null,
    val btnNegativeText: TextResource? = null,
    val showBtnNegative: Boolean = btnNegativeText != null,
    val payload: Parcelable? = null,
    val tag: String? = null,
    val cancelable: Boolean = false,
    @StyleRes val messageTextAppearance: Int? = null,
    @StyleRes val theme: Int? = null,
) : BaseShowEvent()
