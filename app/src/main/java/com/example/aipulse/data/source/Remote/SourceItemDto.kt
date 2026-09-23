package com.example.aipulse.data.source.Remote

import com.example.aipulse.data.source.Domain.SourceData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SourceItemDto(
    val id: String? = null,
    val url : String? = null,
    val name: String? = null,
    val description: String? = null,
)