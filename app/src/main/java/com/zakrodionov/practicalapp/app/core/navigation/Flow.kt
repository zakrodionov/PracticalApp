package com.zakrodionov.practicalapp.app.core.navigation

import android.os.Parcelable
import cafe.adriel.voyager.androidx.AndroidScreen

abstract class Flow : AndroidScreen(), Parcelable {
    abstract val title: String
}
