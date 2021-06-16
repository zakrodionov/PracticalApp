package com.zakrodionov.practicalapp.app.core

import android.os.Parcelable
import androidx.annotation.StyleRes
import androidx.fragment.app.FragmentManager
import com.zakrodionov.common.core.TextResource
import com.zakrodionov.common.dialogs.CommonDialog
import com.zakrodionov.common.extensions.showDialog

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

fun FragmentManager.showDialog(event: ShowDialog) {
    showDialog(
        fragmentManager = this,
        tag = event.tag ?: CommonDialog.TAG_COMMON_DIALOG,
        title = event.title,
        message = event.message,
        btnPositiveText = event.btnPositiveText,
        btnNegativeText = event.btnNegativeText,
        showBtnNegative = event.showBtnNegative,
        payload = event.payload,
        cancelable = event.cancelable,
        messageTextAppearance = event.messageTextAppearance,
        theme = event.theme
    )
}
