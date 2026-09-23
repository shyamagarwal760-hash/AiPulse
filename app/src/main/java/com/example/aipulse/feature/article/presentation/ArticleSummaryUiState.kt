package com.example.aipulse.feature.article.presentation

import com.example.aipulse.data.ai.AiSummary

sealed interface ArticleSummaryUiState {
    data object Idle : ArticleSummaryUiState
    data object Loading : ArticleSummaryUiState

    data class Success(
        val summary: AiSummary,
    ) : ArticleSummaryUiState

    data class Error(
        val message: String,
    ) : ArticleSummaryUiState
}
