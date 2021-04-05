package com.zakrodionov.practicalapp.app.ui.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.zakrodionov.common.extensions.idFromString
import com.zakrodionov.common.extensions.setTextOrHide
import com.zakrodionov.practicalapp.databinding.ItemPostBinding
import com.zakrodionov.practicalapp.domain.model.Posts.Post

enum class PostItemType(val type: Int) {
    POST(0)
}

data class PostItem(val post: Post) : AbstractBindingItem<ItemPostBinding>() {
    override var identifier: Long = post.id?.idFromString() ?: 0L
    override val type: Int = PostItemType.POST.type

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemPostBinding =
        ItemPostBinding.inflate(inflater, parent, false)

    override fun bindView(binding: ItemPostBinding, payloads: List<Any>) {
        binding.tvTitle.setTextOrHide(post.text)
        binding.ivPhoto.load(post.image)
    }
}
