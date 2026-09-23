package com.infilabs.aipulse.data.article.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticleItemDto(
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String ,
    @Json(name = "urlToImage")
    val imageUrl: String? = null,
    @Json(name = "publishedAt")
    val date: String? = null,
)