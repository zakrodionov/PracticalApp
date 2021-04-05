package com.zakrodionov.practicalapp.app.ui.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import coil.load
import com.zakrodionov.common.extensions.setTextOrHide
import com.zakrodionov.practicalapp.app.core.BaseViewHolder
import com.zakrodionov.practicalapp.databinding.ItemPostBinding
import com.zakrodionov.practicalapp.domain.model.Posts.Post

class PostsAdapter(val onItemClick: (Post) -> Unit) :
    PagingDataAdapter<Post, PostsAdapter.PostViewHolder>(POST_COMPARATOR) {

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    inner class PostViewHolder(val binding: ItemPostBinding) : BaseViewHolder<Post>(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick.invoke(item)
            }
        }

        override fun bind(item: Post) {
            super.bind(item)
            with(binding) {
                tvTitle.setTextOrHide(item.text)
                ivPhoto.load(item.image)
            }
        }
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Post>() {
            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem.id == newItem.id

            override fun getChangePayload(oldItem: Post, newItem: Post): Any? = null
        }
    }
}
