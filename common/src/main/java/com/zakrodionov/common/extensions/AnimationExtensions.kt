@file:Suppress("MagicNumber")

package com.zakrodionov.common.extensions

import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.Animation
import android.view.animation.Transformation

fun View.toggleArrow(delay: Long = 200): Boolean =
    when (rotation) {
        0f -> {
            animate().apply {
                duration = delay
                rotation(180f)
            }
            true
        }
        else -> {
            animate().apply {
                duration = delay
                rotation(0f)
            }
            false
        }
    }

fun View.toggleArrow(show: Boolean, delay: Long = 200): Boolean =
    when {
        show -> {
            animate().apply {
                duration = delay
                rotation(180f)
            }
            true
        }
        else -> {
            animate().apply {
                duration = delay
                rotation(0f)
            }
            false
        }
    }

fun View.rotate(degree: Float, delay: Long = 200): Boolean =
    when (rotation) {
        0f -> {
            animate().apply {
                duration = delay
                rotation(degree)
            }
            true
        }
        else -> {
            animate().apply {
                duration = delay
                rotation(0f)
            }
            false
        }
    }

fun View.toggleCollapse(isCollapse: Boolean) {
    if (isCollapse) collapse() else expand()
}

fun View.expand() {
    val matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec((parent as View).width, View.MeasureSpec.EXACTLY)
    val wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    measure(matchParentMeasureSpec, wrapContentMeasureSpec)
    val targetHeight = measuredHeight

    // Older versions of android (pre API 21) cancel animations for views with a height of 0.
    layoutParams.height = 1
    visibility = View.VISIBLE
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            layoutParams.height =
                if (interpolatedTime == 1f) WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
            requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    // Expansion speed of 1dp/ms
    a.duration = (targetHeight / context.resources.displayMetrics.density).toLong()
    startAnimation(a)
}

fun View.collapse() {
    val initialHeight = measuredHeight
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            if (interpolatedTime == 1f) {
                visibility = View.GONE
            } else {
                layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                requestLayout()
            }
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    // Collapse speed of 1dp/ms
    a.duration = (initialHeight / context.resources.displayMetrics.density).toLong()
    startAnimation(a)
}

fun View.toggleAlpha(isShow: Boolean, delay: Long = 200, invisibleMode: Int = View.GONE) {
    if (isShow) animateAlpha(View.VISIBLE, delay) else animateAlpha(invisibleMode, delay)
}

fun View.animateAlpha(visibility: Int, delay: Long = 200) {
    if (visibility == View.VISIBLE) {
        setVisibility(View.VISIBLE)
    }

    val alpha = when (visibility) {
        View.GONE, View.INVISIBLE -> 0f
        View.VISIBLE -> 1f
        else -> 1f
    }

    animate().apply {
        duration = delay
        alpha(alpha)
        withEndAction {
            setVisibility(visibility)
        }
    }
}
