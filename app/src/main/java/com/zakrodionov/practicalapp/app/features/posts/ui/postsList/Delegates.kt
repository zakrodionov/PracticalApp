package com.zakrodionov.practicalapp.app.features.posts.ui.postsList

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.zakrodionov.common.extensions.capitalizeFirstLetter
import com.zakrodionov.common.extensions.load
import com.zakrodionov.common.extensions.setTextOrHide
import com.zakrodionov.common.ui.rv.DiffItem
import com.zakrodionov.practicalapp.app.features.posts.domain.model.Posts.Post
import com.zakrodionov.practicalapp.databinding.ItemPostBinding

fun postDelegate(onItemClick: (Post) -> Unit) = adapterDelegateViewBinding<Post, DiffItem, ItemPostBinding>(
    { inflater, root -> ItemPostBinding.inflate(inflater, root, false) }) {

    binding.root.setOnClickListener {
        onItemClick.invoke(item)
    }

    bind {
        with(binding) {
            tvTitle.setTextOrHide(item.text?.capitalizeFirstLetter())
            ivPhoto.load(item.image)
        }
    }
}
