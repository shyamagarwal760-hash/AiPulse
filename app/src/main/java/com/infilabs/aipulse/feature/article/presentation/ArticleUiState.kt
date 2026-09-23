package com.infilabs.aipulse.feature.article.presentation

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

sealed interface ArticleUiState {
    data object Loading : ArticleUiState

    data class Success(
        val articles: Flow<PagingData<ArticleUiModel>>,
    ) : ArticleUiState

    data class Error(
        val message: String,
    ) : ArticleUiState
}
