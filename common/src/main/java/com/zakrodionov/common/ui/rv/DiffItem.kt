package com.zakrodionov.common.ui.rv

import android.os.Parcelable

interface DiffItem : DisplayableItem, Parcelable {
    val itemId: String
}
