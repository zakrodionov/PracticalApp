package com.zakrodionov.practicalapp.app.ui.about

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AboutState(
    val isLogged: Boolean = false
) : Parcelable

sealed class AboutEvent
