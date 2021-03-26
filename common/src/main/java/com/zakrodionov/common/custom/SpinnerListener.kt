package com.zakrodionov.common.custom

import android.view.View
import android.widget.AdapterView

interface SpinnerListener : AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) = Unit

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        onItemSelected(position)
    }

    fun onItemSelected(position: Int)
}
