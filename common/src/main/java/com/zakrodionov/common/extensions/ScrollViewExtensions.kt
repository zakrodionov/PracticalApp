package com.zakrodionov.common.extensions

import android.graphics.Rect
import android.os.Handler
import android.view.View
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView

fun View.isFullyVisible(scrollView: ScrollView): Boolean {
    val scrollBounds = Rect()
    scrollView.getDrawingRect(scrollBounds)
    val top = y
    val bottom = top + height
    return scrollBounds.top < top && scrollBounds.bottom > bottom
}

fun ScrollView.scrollToBottom() {
    val lastChild = getChildAt(childCount - 1)
    val bottom = lastChild.bottom + paddingBottom
    val delta = bottom - (scrollY + height)
    smoothScrollBy(0, delta)
}

fun NestedScrollView.scrollToBottom() {
    val lastChild = getChildAt(childCount - 1)
    val bottom = lastChild.bottom + paddingBottom
    val delta = bottom - (scrollY + height)
    smoothScrollBy(0, delta)
}

fun NestedScrollView.scrollToBottomWithDelay(delay: Long) {
    Handler().postDelayed({ this.scrollToBottom() }, delay)
}

fun ScrollView.scrollToBottom(targetChildId: Int) {
    val targetChild = findViewById<View>(targetChildId)
    targetChild?.let {
        val bottom = targetChild.bottom + paddingBottom
        val delta = bottom - (scrollY + height)
        smoothScrollBy(0, delta)
    }
}
