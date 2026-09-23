package com.example.aipulse.data.article.remote

import com.example.aipulse.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApi {
    @GET("top-headlines")
    suspend fun getArticles(
        @Query("country") country: String = "us",
        @Query("category") category: String = "business",
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): ArticleResponse
}