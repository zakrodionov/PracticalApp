package com.zakrodionov.common.dialogs

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommonDialogPayload(val value: String) : Parcelable
