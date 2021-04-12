package com.zakrodionov.practicalapp.app.core

import android.os.Parcelable
import androidx.annotation.StringRes
import com.zakrodionov.common.ui.models.ResourceString

sealed class ShowAction {
    data class ShowToast(val message: ResourceString) : ShowAction()
    data class ShowSnackbar(val message: ResourceString, val payload: Parcelable? = null) : ShowAction()
    data class ShowDialog(
        val title: ResourceString,
        val message: ResourceString,
        val payload: Parcelable? = null,
        @StringRes val btnPositiveText: Int? = null,
        @StringRes val btnNegativeText: Int? = null,
        val showBtnNegative: Boolean = false
    ) : ShowAction()
}
