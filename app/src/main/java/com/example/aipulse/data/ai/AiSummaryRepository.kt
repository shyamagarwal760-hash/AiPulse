package com.example.aipulse.data.ai

interface AiSummaryRepository {
    suspend fun getAiSummary(title: List<String>): Result<AiSummary>
}
