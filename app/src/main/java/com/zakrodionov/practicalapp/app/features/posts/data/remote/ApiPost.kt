package com.zakrodionov.practicalapp.app.features.posts.data.remote

import com.zakrodionov.practicalapp.app.features.posts.domain.model.Posts
import com.zakrodionov.practicalapp.app.features.posts.domain.model.Posts.Post
import com.zakrodionov.practicalapp.domain.repository.DEFAULT_PAGE_SIZE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiPost {

    @GET(PATH_GET_POSTS)
    suspend fun getPosts(@Query("page") page: Int, @Query("limit") limit: Int = DEFAULT_PAGE_SIZE): Posts

    @GET(PATH_GET_POST)
    suspend fun getPost(@Path("postId") id: String): Post

    companion object {
        const val PATH_GET_POSTS = "post/"
        const val PATH_GET_POST = "post/{postId}"
    }
}
