package com.example.aipulse.feature.source.presentation

import kotlinx.coroutines.flow.Flow

sealed interface SourceUiState {
    data object Loading : SourceUiState

    data class Success(
        val sources: List<SourceUiModel>,
    ) : SourceUiState

    data class Error(
        val message: String,
    ) : SourceUiState
}
