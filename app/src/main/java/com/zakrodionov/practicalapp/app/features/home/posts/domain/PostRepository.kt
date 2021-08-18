package com.zakrodionov.practicalapp.app.features.home.posts.domain

import com.zakrodionov.practicalapp.app.core.Result
import com.zakrodionov.practicalapp.app.features.home.posts.domain.model.Posts.Post

const val DEFAULT_PAGE_SIZE = 20

interface PostRepository {
    suspend fun getPosts(page: Int): Result<List<Post>>
    suspend fun getPost(id: String): Result<Post>
}
