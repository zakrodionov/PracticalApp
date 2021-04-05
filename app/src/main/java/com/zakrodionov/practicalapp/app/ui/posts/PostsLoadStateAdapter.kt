package com.zakrodionov.practicalapp.app.ui.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadState.Error
import androidx.paging.LoadStateAdapter
import com.zakrodionov.practicalapp.app.core.BaseViewHolder
import com.zakrodionov.practicalapp.databinding.ItemNetworkStateBinding

class PostsLoadStateAdapter(
    private val retryCallback: () -> Unit
) : LoadStateAdapter<PostsLoadStateAdapter.NetworkStateItemViewHolder>() {
    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateItemViewHolder {
        val binding = ItemNetworkStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NetworkStateItemViewHolder(binding)
    }

    inner class NetworkStateItemViewHolder(
        val binding: ItemNetworkStateBinding
    ) : BaseViewHolder<LoadState>(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retryCallback() }
        }

        fun bindTo(loadState: LoadState) {
            with(binding) {
                progressBar.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState is Error
                errorMsg.isVisible = !(loadState as? Error)?.error?.message.isNullOrBlank()
                errorMsg.text = (loadState as? Error)?.error?.message
            }
        }
    }
}
