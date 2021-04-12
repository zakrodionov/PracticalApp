package com.zakrodionov.practicalapp.app.core

import android.os.Parcelable
import androidx.annotation.StyleRes
import com.zakrodionov.common.core.ResourceString

sealed class ShowAction {
    data class ShowToast(val message: ResourceString) : ShowAction()
    data class ShowSnackbar(val message: ResourceString, val payload: Parcelable? = null) : ShowAction()
    data class ShowDialog(
        val title: ResourceString? = null,
        val message: ResourceString? = null,
        val btnPositiveText: ResourceString? = null,
        val btnNegativeText: ResourceString? = null,
        val showBtnNegative: Boolean = btnNegativeText != null,
        val payload: Parcelable? = null,
        val tag: String? = null,
        val reverse: Boolean = false,
        val cancelable: Boolean = false,
        @StyleRes val messageTextAppearance: Int? = null,
        @StyleRes val theme: Int? = null
    ) : ShowAction()
}
