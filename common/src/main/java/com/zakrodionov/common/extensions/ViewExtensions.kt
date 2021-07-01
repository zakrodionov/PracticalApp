@file:Suppress("TooManyFunctions")

package com.zakrodionov.common.extensions

import android.content.res.Resources
import android.graphics.Outline
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

val Int.pxToDp get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.dpToPx get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.pxToDpF get() = (this / Resources.getSystem().displayMetrics.density)

val Int.dpToPxF get() = (this * Resources.getSystem().displayMetrics.density)

fun View.hide() = run { visibility = View.INVISIBLE }

fun View.show() = run { visibility = View.VISIBLE }

fun View.gone() = run { visibility = View.GONE }

inline fun View.showIf(invisibleMode: Int = View.GONE, condition: View.() -> Boolean) {
    visibility = if (condition()) View.VISIBLE else invisibleMode
}

inline fun View.hideIf(invisibleMode: Int = View.GONE, condition: View.() -> Boolean) {
    visibility = if (condition()) invisibleMode else View.VISIBLE
}

val View.screenLocation: IntArray
    get(): IntArray {
        val point = IntArray(2)
        getLocationOnScreen(point)
        return point
    }

val View.boundingBox: Rect
    get(): Rect {
        val (x, y) = screenLocation
        return Rect(x, y, x + width, y + height)
    }

val View.rect: Rect
    get() = Rect(0, 0, width, height)

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

/** Requests focus and shows keyboard for [this] view if it is possible. */
fun View.showKeyboard() {
    ViewCompat.getWindowInsetsController(this)
        ?.show(WindowInsetsCompat.Type.ime())
}

fun View.hideKeyboard() {
    ViewCompat.getWindowInsetsController(this)
        ?.hide(WindowInsetsCompat.Type.ime())
}

fun View.imeVisibilityListener(onVisibilityChanged: (Boolean) -> Unit) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
        onVisibilityChanged(isKeyboardVisible)
        insets
    }
}

val View.isKeyboardVisible: Boolean
    get() = ViewCompat.getRootWindowInsets(this)
        ?.isVisible(WindowInsetsCompat.Type.ime()) == true

fun WebView.loadUtf8Data(html: String) {
    loadData(html, "text/html; charset=UTF-8;", "utf-8")
}

fun View.clickWithDebounce(debounceTime: Long = 600L, action: () -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

fun View.isInTouchArea(event: MotionEvent): Boolean {
    return event.x.toInt() in left..right &&
        event.y.toInt() in top..bottom
}

@Suppress("LongParameterList")
fun View.showSnackbar(
    text: CharSequence,
    gravity: Int = Gravity.BOTTOM,
    length: Int = Snackbar.LENGTH_LONG,
    background: Drawable? = null,
    actionText: String = "",
    action: (() -> Unit)? = null,
): Snackbar {
    val snackbar = Snackbar.make(this, text, length)

    with(snackbar) {
        when (val params = view.layoutParams) {
            is CoordinatorLayout.LayoutParams -> {
                params.gravity = gravity
                view.layoutParams = params
            }
            is FrameLayout.LayoutParams -> {
                params.gravity = gravity
                view.layoutParams = params
            }
            is LinearLayout.LayoutParams -> {
                params.gravity = gravity
                view.layoutParams = params
            }
        }

        background?.let {
            view.background = it
        }

        action?.let {
            setAction(actionText) { action.invoke() }
        }

        show()
    }

    return snackbar
}

fun View.setRoundCorner(
    radius: Float = 15.dpToPxF,
    onlyTop: Boolean = false,
) {
    val viewOutlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            val height = if (onlyTop) (height + radius).toInt() else height
            outline.setRoundRect(0, 0, width, height, radius)
        }
    }
    outlineProvider = viewOutlineProvider
    clipToOutline = true
}
