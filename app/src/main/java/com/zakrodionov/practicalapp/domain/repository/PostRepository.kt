package com.zakrodionov.practicalapp.domain.repository

import com.zakrodionov.practicalapp.app.core.Result
import com.zakrodionov.practicalapp.domain.model.Posts.Post

interface PostRepository {
    suspend fun getPosts(): Result<List<Post>>
    suspend fun getPost(id: String): Result<Post>
}
