package com.example.aipulse.data.article.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArticleResponse(
    val status: String? = null,
    val totalResults: Int? = null,
    @Json(name = "articles")
    val articles: List<ArticleItemDto> = emptyList(),
)