package com.zakrodionov.practicalapp.app.features.posts.data

import com.zakrodionov.practicalapp.app.core.Executor
import com.zakrodionov.practicalapp.app.core.Result
import com.zakrodionov.practicalapp.app.features.posts.data.remote.ApiPosts
import com.zakrodionov.practicalapp.app.features.posts.domain.PostRepository
import com.zakrodionov.practicalapp.app.features.posts.domain.model.Posts.Post

class PostRepositoryImpl(private val executor: Executor, private val apiPosts: ApiPosts) : PostRepository {

    override suspend fun getPosts(page: Int): Result<List<Post>> = executor.execute {
        apiPosts.getPosts(page).posts.orEmpty()
    }

    override suspend fun getPost(id: String): Result<Post> = executor.execute {
        apiPosts.getPost(id)
    }
}
