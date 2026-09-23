package com.example.aipulse.data.source.Remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SourceResponse(
    val status: String? = null,
    val totalResults: Int? = null,
    @Json(name = "sources")
    val sources: List<SourceItemDto> = emptyList(),
)