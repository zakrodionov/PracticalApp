@file:Suppress("MagicNumber", "TooManyFunctions")

package com.zakrodionov.common.extensions

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.TextView
import androidx.core.animation.doOnEnd
import com.google.android.material.animation.AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR

fun View.toggleArrow(duration: Long = 200): Boolean =
    when (rotation) {
        0f -> {
            animate()
                .setDuration(duration)
                .rotation(180f)
            true
        }
        else -> {
            animate()
                .setDuration(duration)
                .rotation(0f)
            false
        }
    }

fun View.toggleArrow(show: Boolean, duration: Long = 200): Boolean =
    when {
        show -> {
            animate()
                .setDuration(duration)
                .rotation(180f)
            true
        }
        else -> {
            animate()
                .setDuration(duration)
                .rotation(0f)
            false
        }
    }

fun View.rotate(degree: Float, duration: Long = 200): Boolean =
    when (rotation) {
        0f -> {
            animate()
                .setDuration(duration)
                .rotation(degree)
            true
        }
        else -> {
            animate()
                .setDuration(duration)
                .rotation(0f)
            false
        }
    }

fun View.toggleCollapse(isCollapse: Boolean) {
    if (isCollapse) collapse() else expand()
}

fun View.expand() {
    val matchParentMeasureSpec =
        View.MeasureSpec.makeMeasureSpec((parent as View).width, View.MeasureSpec.EXACTLY)
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

fun View.toggleAlpha(isShow: Boolean, duration: Long = 200, invisibleMode: Int = View.GONE) {
    if (isShow) animateAlpha(View.VISIBLE, duration) else animateAlpha(invisibleMode, duration)
}

fun View.animateAlpha(visibility: Int, duration: Long = 200) {
    if (visibility == View.VISIBLE) {
        setVisibility(View.VISIBLE)
    }

    val alpha = when (visibility) {
        View.GONE, View.INVISIBLE -> 0f
        View.VISIBLE -> 1f
        else -> 1f
    }

    animate()
        .setDuration(duration)
        .alpha(alpha)
        .withEndAction {
            setVisibility(visibility)
        }
}

/** [animateViewIn], [animateViewOut]
 * Можно обернуть view во ViewGroup чтобы вьюшка ны выходило за свои границы во время анимации,
 * так же иногда нужно использовать doOnLayout
 */
fun View.animateViewIn(duration: Long = 200) {
    translationY = -height.toFloat()
    animate()
        .translationY(0f)
        .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
        .setDuration(duration)
        .start()
}

fun View.animateViewOut(duration: Long = 200) {
    animate()
        .translationY(-height.toFloat())
        .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
        .setDuration(duration)
        .start()
}

fun TextView.animateSetTextLikeSlotUp(text: String, duration: Long = 200) {
    val translationYOut = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, 0f, -height.toFloat())
    translationYOut.duration = duration
    translationYOut.doOnEnd {
        translationY = height.toFloat()
        this.text = text
    }

    val translationYIn = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, height.toFloat(), 0f)
    translationYIn.duration = duration

    val set = AnimatorSet()
    set.playSequentially(translationYOut, translationYIn)
    set.start()
}

fun TextView.animateSetTextLikeSlotDown(text: String, duration: Long = 200) {
    val translationYOut = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, 0f, height.toFloat())
    translationYOut.duration = duration
    translationYOut.doOnEnd {
        translationY = -height.toFloat()
        this.text = text
    }

    val translationYIn = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, -height.toFloat(), 0f)
    translationYIn.duration = duration

    val set = AnimatorSet()
    set.playSequentially(translationYOut, translationYIn)
    set.start()
}
