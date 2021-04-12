package com.zakrodionov.practicalapp.app.core

import android.os.Parcelable
import com.zakrodionov.common.ui.models.ResourceString

sealed class ShowAction {
    data class ShowToast(val message: ResourceString) : ShowAction()
    data class ShowSnackbar(val message: ResourceString, val payload: Parcelable? = null) : ShowAction()
    data class ShowDialog(
        val title: ResourceString? = null,
        val message: ResourceString? = null,
        val btnPositiveText: ResourceString? = null,
        val btnNegativeText: ResourceString? = null,
        val showBtnNegative: Boolean = false,
        val payload: Parcelable? = null
    ) : ShowAction()
}
