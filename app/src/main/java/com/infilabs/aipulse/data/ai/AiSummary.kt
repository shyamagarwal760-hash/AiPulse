package com.infilabs.aipulse.data.ai

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AiSummary(
    val summary: String,
    val sentiment: String,
)
