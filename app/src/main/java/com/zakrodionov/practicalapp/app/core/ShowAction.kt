package com.zakrodionov.practicalapp.app.core

import android.os.Parcelable
import androidx.annotation.StyleRes
import com.zakrodionov.common.core.TextResource

sealed class ShowAction {
    data class ShowToast(val message: TextResource) : ShowAction()
    data class ShowSnackbar(val message: TextResource, val payload: Parcelable? = null) : ShowAction()
    data class ShowDialog(
        val title: TextResource? = null,
        val message: TextResource? = null,
        val btnPositiveText: TextResource? = null,
        val btnNegativeText: TextResource? = null,
        val showBtnNegative: Boolean = btnNegativeText != null,
        val payload: Parcelable? = null,
        val tag: String? = null,
        val reverse: Boolean = false,
        val cancelable: Boolean = false,
        @StyleRes val messageTextAppearance: Int? = null,
        @StyleRes val theme: Int? = null
    ) : ShowAction()
}
