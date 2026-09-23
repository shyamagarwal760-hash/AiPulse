package com.example.aipulse.feature.article.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aipulse.data.ai.AiSummaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.text.isNotBlank

@HiltViewModel
class ArticleSummaryViewModel @Inject constructor(
    private val aiSummaryRepository: AiSummaryRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ArticleSummaryUiState>(ArticleSummaryUiState.Idle)
    val uiState: StateFlow<ArticleSummaryUiState> = _uiState.asStateFlow()

    fun summarize(articles: List<ArticleUiModel>) {
        val titles = articles.mapNotNull { it.title?.takeIf(String::isNotBlank) }
        if (titles.isEmpty()) {
            _uiState.value = ArticleSummaryUiState.Error("There are no article titles to summarize.")
            return
        }

        _uiState.value = ArticleSummaryUiState.Loading
        viewModelScope.launch {
            aiSummaryRepository.getAiSummary(titles)
                .onSuccess { summary ->
                    _uiState.value = ArticleSummaryUiState.Success(summary)
                }
                .onFailure { error ->
                    Timber.e(error, "Unable to generate article summary")
                    _uiState.value = ArticleSummaryUiState.Error(
                        error.message ?: "Unable to generate a summary.",
                    )
                }
        }
    }

    fun reset() {
        _uiState.value = ArticleSummaryUiState.Idle
    }
}
