package com.zakrodionov.practicalapp.app.features.home.posts.domain.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.zakrodionov.common.ui.rv.DiffItem
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
data class Posts(
    @Json(name = "data")
    val posts: List<Post>?,
    @Json(name = "limit")
    val limit: Int?,
    @Json(name = "offset")
    val offset: Int?,
    @Json(name = "page")
    val page: Int?,
    @Json(name = "total")
    val total: Int?
) {
    @JsonClass(generateAdapter = true)
    @Parcelize
    data class Post(
        @Json(name = "id")
        val id: String?,
        @Json(name = "image")
        val image: String?,
        @Json(name = "likes")
        val likes: Int?,
        @Json(name = "link")
        val link: String?,
        @Json(name = "owner")
        val owner: Owner?,
        @Json(name = "publishDate")
        val publishDate: String?,
        @Json(name = "tags")
        val tags: List<String?>?,
        @Json(name = "text")
        val text: String?
    ) : DiffItem {
        @JsonClass(generateAdapter = true)
        @Parcelize
        data class Owner(
            @Json(name = "email")
            val email: String?,
            @Json(name = "firstName")
            val firstName: String?,
            @Json(name = "id")
            val id: String?,
            @Json(name = "lastName")
            val lastName: String?,
            @Json(name = "picture")
            val picture: String?,
            @Json(name = "title")
            val title: String?
        ) : Parcelable

        override val itemId: String
            get() = id.orEmpty()
    }
}
