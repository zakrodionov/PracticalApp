package com.zakrodionov.common.extensions

import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun ImageView.setColorFilterCompat(@ColorRes color: Int) =
    setColorFilter(ContextCompat.getColor(context, color))
