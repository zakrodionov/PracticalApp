package com.zakrodionov.practicalapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zakrodionov.practicalapp.app.core.Executor
import com.zakrodionov.practicalapp.app.core.Result
import com.zakrodionov.practicalapp.data.remote.ApiPost
import com.zakrodionov.practicalapp.data.remote.PostsPagingDataSource
import com.zakrodionov.practicalapp.domain.model.Posts.Post
import com.zakrodionov.practicalapp.domain.repository.DEFAULT_PAGE_SIZE
import com.zakrodionov.practicalapp.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class PostRepositoryImpl(private val executor: Executor, private val apiPost: ApiPost) : PostRepository {

    override fun getPosts(): Flow<PagingData<Post>> =
        Pager(PagingConfig(DEFAULT_PAGE_SIZE)) { PostsPagingDataSource(apiPost, DEFAULT_PAGE_SIZE) }.flow

    override suspend fun getPost(id: String): Result<Post> = executor.execute {
        apiPost.getPost(id)
    }
}
