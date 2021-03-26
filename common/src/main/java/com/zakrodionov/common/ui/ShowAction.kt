package com.zakrodionov.common.ui

import android.os.Parcelable
import com.zakrodionov.common.ui.models.ResourceString

sealed class ShowAction {
    data class ShowToast(val message: ResourceString) : ShowAction()
    data class ShowSnackbar(val message: ResourceString, val payload: Parcelable? = null) : ShowAction()
    data class ShowDialog(val title: ResourceString, val message: ResourceString, val payload: Parcelable? = null) :
        ShowAction()
}
