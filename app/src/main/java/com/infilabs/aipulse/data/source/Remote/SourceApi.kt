package com.infilabs.aipulse.data.source.Remote

import com.infilabs.aipulse.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface SourceApi {
    @GET("sources")
    suspend fun getSources(
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY,
    ): SourceResponse
}