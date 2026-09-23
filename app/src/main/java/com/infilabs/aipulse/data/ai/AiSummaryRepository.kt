package com.infilabs.aipulse.data.ai

interface AiSummaryRepository {
    suspend fun getAiSummary(title: List<String>): Result<AiSummary>
}
