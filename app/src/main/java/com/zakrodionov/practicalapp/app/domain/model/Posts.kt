package com.zakrodionov.practicalapp.app.domain.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.zakrodionov.common.ui.DiffItem
import kotlinx.parcelize.Parcelize
import java.io.Serializable

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
    val total: Int?,
) {
    @JsonClass(generateAdapter = true)
    @Parcelize
    data class Post(
        @Json(name = "id")
        val id: String? = null,
        @Json(name = "image")
        val image: String? = null,
        @Json(name = "likes")
        val likes: Int? = null,
        @Json(name = "link")
        val link: String? = null,
        @Json(name = "owner")
        val owner: Owner? = null,
        @Json(name = "publishDate")
        val publishDate: String? = null,
        @Json(name = "tags")
        val tags: List<String?>? = null,
        @Json(name = "text")
        val text: String? = null,
    ) : DiffItem, Serializable {
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
            val title: String?,
        ) : Parcelable, Serializable

        override val itemId: String
            get() = id.orEmpty()
    }
}
