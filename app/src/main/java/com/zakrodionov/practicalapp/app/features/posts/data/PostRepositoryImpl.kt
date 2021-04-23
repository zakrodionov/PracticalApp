package com.zakrodionov.practicalapp.app.features.posts.data

import com.zakrodionov.practicalapp.app.core.Executor
import com.zakrodionov.practicalapp.app.core.Result
import com.zakrodionov.practicalapp.app.features.posts.data.remote.ApiPost
import com.zakrodionov.practicalapp.app.features.posts.domain.PostRepository
import com.zakrodionov.practicalapp.app.features.posts.domain.model.Posts.Post

class PostRepositoryImpl(private val executor: Executor, private val apiPost: ApiPost) : PostRepository {

    override suspend fun getPosts(page: Int): Result<List<Post>> = executor.execute {
        apiPost.getPosts(page).posts.orEmpty()
    }

    override suspend fun getPost(id: String): Result<Post> = executor.execute {
        apiPost.getPost(id)
    }
}
