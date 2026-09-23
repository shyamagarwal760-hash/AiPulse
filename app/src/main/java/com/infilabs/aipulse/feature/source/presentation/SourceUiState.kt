package com.infilabs.aipulse.feature.source.presentation

sealed interface SourceUiState {
    data object Loading : SourceUiState

    data class Success(
        val sources: List<SourceUiModel>,
    ) : SourceUiState

    data class Error(
        val message: String,
    ) : SourceUiState
}
