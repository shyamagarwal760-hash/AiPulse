package com.infilabs.aipulse.data.ai

import com.squareup.moshi.JsonClass
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AiApi {

    @POST("v1/responses")
    suspend fun analyzeArticle(
        @Header("Authorization") authorization: String,
        @Body request: OpenAiRequest
    ): OpenAiResponse
}

@JsonClass(generateAdapter = true)
data class OpenAiRequest(
    val model: String,
    val input: List<Message>
)

@JsonClass(generateAdapter = true)
data class OpenAiResponse(
    val id: String? = null,
    val output: List<OpenAiOutput>? = null
)

@JsonClass(generateAdapter = true)
data class OpenAiOutput(
    val id: String? = null,
    val type: String? = null,
    val role: String? = null,
    val status: String? = null,
    val content: List<OpenAiContent>? = null
)

@JsonClass(generateAdapter = true)
data class OpenAiContent(
    val type: String? = null,
    val text: String? = null
)

@JsonClass(generateAdapter = true)
data class Message(
    val role: String? = null,
    val content: String? = null
)