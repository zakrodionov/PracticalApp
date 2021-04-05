package com.zakrodionov.practicalapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zakrodionov.practicalapp.domain.model.Posts.Post
import kotlinx.coroutines.delay

class PostsPagingDataSource(private val apiPost: ApiPost, private val size: Int) : PagingSource<Int, Post>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        val pageNumber = params.key ?: 0
        return try {
            delay(2000) // TODO
            val data = apiPost.getPosts(pageNumber)
            val nextPageNumber: Int? = if (data.posts?.size ?: 0 < size) null else pageNumber + 1
            LoadResult.Page(data = data.posts.orEmpty(), prevKey = null, nextKey = nextPageNumber)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    // TODO
    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            // This loads starting from previous page, but since PagingConfig.initialLoadSize spans
            // multiple pages, the initial load will still load items centered around
            // anchorPosition. This also prevents needing to immediately launch prepend due to
            // prefetchDistance.
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}
