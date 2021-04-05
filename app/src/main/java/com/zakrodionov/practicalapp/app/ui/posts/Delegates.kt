package com.zakrodionov.practicalapp.app.ui.posts

import coil.load
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.zakrodionov.common.extensions.setTextOrHide
import com.zakrodionov.common.ui.rv.DiffItem
import com.zakrodionov.practicalapp.databinding.ItemPostBinding
import com.zakrodionov.practicalapp.domain.model.Posts.Post

fun postDelegate(onItemClick: (Post) -> Unit) = adapterDelegateViewBinding<Post, DiffItem, ItemPostBinding>(
    { inflater, root -> ItemPostBinding.inflate(inflater, root, false) }) {

    binding.root.setOnClickListener {
        onItemClick.invoke(item)
    }

    bind {
        with(binding) {
            tvTitle.setTextOrHide(item.text)
            ivPhoto.load(item.image)
        }
    }
}
