package com.zakrodionov.practicalapp.app.data.repositories

import com.zakrodionov.practicalapp.app.core.Executor
import com.zakrodionov.practicalapp.app.core.Result
import com.zakrodionov.practicalapp.app.data.api.ApiPosts
import com.zakrodionov.practicalapp.app.domain.model.Posts.Post
import com.zakrodionov.practicalapp.app.domain.repositories.PostRepository

class PostRepositoryImpl(private val executor: Executor, private val apiPosts: ApiPosts) : PostRepository {

    override suspend fun getPosts(page: Int): Result<List<Post>> = executor.execute {
        apiPosts.getPosts(page).posts.orEmpty()
    }

    override suspend fun getPost(id: String): Result<Post> = executor.execute {
        apiPosts.getPost(id)
    }
}
