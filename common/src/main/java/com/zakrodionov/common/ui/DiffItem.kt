package com.zakrodionov.common.ui

import android.os.Parcelable

interface DisplayableItem

interface DiffItem : DisplayableItem, Parcelable {
    val itemId: String
}
