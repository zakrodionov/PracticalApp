package com.zakrodionov.practicalapp.domain.repository

import androidx.paging.PagingData
import com.zakrodionov.practicalapp.app.core.Result
import com.zakrodionov.practicalapp.domain.model.Posts.Post
import kotlinx.coroutines.flow.Flow

const val DEFAULT_PAGE_SIZE = 20

interface PostRepository {
    fun getPosts(): Flow<PagingData<Post>>
    suspend fun getPost(id: String): Result<Post>
}
