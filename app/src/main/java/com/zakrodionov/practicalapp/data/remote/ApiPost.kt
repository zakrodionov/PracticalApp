package com.zakrodionov.practicalapp.data.remote

import com.zakrodionov.practicalapp.domain.model.Posts
import com.zakrodionov.practicalapp.domain.model.Posts.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiPost {

    @GET(PATH_GET_POSTS)
    suspend fun getPosts(): Posts

    @GET(PATH_GET_POST)
    suspend fun getPost(@Path("postId") id: String): Post

    companion object {
        const val PATH_GET_POSTS = "post/"
        const val PATH_GET_POST = "post/{postId}"
    }
}
