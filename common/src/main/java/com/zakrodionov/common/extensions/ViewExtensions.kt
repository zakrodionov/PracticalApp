@file:Suppress("TooManyFunctions")

package com.zakrodionov.common.extensions

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar

val Int.pxToDp get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.dpToPx get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.pxToDpF get() = (this / Resources.getSystem().displayMetrics.density)

val Int.dpToPxF get() = (this * Resources.getSystem().displayMetrics.density)

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun View.hide() = run { visibility = View.INVISIBLE }

fun View.show() = run { visibility = View.VISIBLE }

fun View.gone() = run { visibility = View.GONE }

fun View.showKeyboard() {
    this.requestFocus()
    val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun TextView.setOnEnterClickListener(action: (TextView) -> Unit) {
    setOnEditorActionListener { textView, actionId, keyEvent ->
        if (keyEvent?.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
            action(textView)
        }
        false
    }
}

fun EditText.setTextWithSelection(text: String) {
    setText(text)
    setSelection(text.length)
}

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

@Suppress("LongParameterList")
fun showSnackbar(
    parentView: View,
    text: CharSequence,
    gravity: Int = Gravity.BOTTOM,
    length: Int = Snackbar.LENGTH_LONG,
    background: Drawable? = null,
    actionText: String = "",
    action: (() -> Unit)? = null,
): Snackbar {
    val snackbar = Snackbar.make(parentView, text, length)

    with(snackbar) {
        val params = view.layoutParams as? CoordinatorLayout.LayoutParams
        params?.let {
            params.gravity = gravity
            view.layoutParams = params
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
