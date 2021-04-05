package com.zakrodionov.common.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.RequestBuilder
import com.zakrodionov.common.glide.GlideApp

fun ImageView.setColorFilterCompat(@ColorRes color: Int) =
    setColorFilter(ContextCompat.getColor(context, color))

fun ImageView.load(
    url: String?,
    customize: (RequestBuilder<Drawable>.() -> RequestBuilder<Drawable>)? = null
) = GlideApp.with(context)
    .load(url)
    .also { glideRequest ->
        if (customize != null) {
            glideRequest.customize()
        }
    }
    .into(this)
