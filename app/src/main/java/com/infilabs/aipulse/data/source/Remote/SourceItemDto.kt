package com.infilabs.aipulse.data.source.Remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SourceItemDto(
    val id: String? = null,
    val url : String? = null,
    val name: String? = null,
    val description: String? = null,
)