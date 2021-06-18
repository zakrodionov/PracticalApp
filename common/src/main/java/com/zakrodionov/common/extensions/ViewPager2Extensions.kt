package com.zakrodionov.common.extensions

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.zakrodionov.common.ui.rv.HorizontalMarginItemDecoration
import kotlin.math.abs

@Suppress("MagicNumber")
fun ViewPager2.addHorizontalPagePreview(
    nextItemVisiblePx: Int = 20.dpToPx,
    currentItemHorizontalMarginPx: Int = 40.dpToPx,
    scaleEffect: Boolean = false,
    fadingEffect: Boolean = false,
) {
    offscreenPageLimit = 1
    // Add a PageTransformer that translates the next and previous items horizontally
    // towards the center of the screen, which makes them visible
    val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
    val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
        page.translationX = -pageTranslationX * position

        if (scaleEffect) {
            page.scaleY = 1 - (0.25f * abs(position))
        }

        if (fadingEffect) {
            page.alpha = 0.25f + (1 - abs(position))
        }
    }
    setPageTransformer(pageTransformer)

    // The ItemDecoration gives the current (centered) item horizontal margin so that
    // it doesn't occupy the whole screen width. Without it the items overlap
    val itemDecoration = HorizontalMarginItemDecoration(currentItemHorizontalMarginPx)
    addItemDecoration(itemDecoration)
}
