package com.zakrodionov.practicalapp.app.core

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<T : Any>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    lateinit var item: T

    open fun bind(item: T) {
        this.item = item
    }
}
