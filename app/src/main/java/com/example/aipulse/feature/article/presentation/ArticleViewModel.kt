package com.example.aipulse.feature.article.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.aipulse.data.article.Mapper.toArticleData
import com.example.aipulse.data.article.room.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val articleRepository: ArticleRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ArticleUiState>(ArticleUiState.Loading)
    val uiState: StateFlow<ArticleUiState> = _uiState.asStateFlow()

    init {
        loadArticles()
    }

    private fun loadArticles() {
      //  Log.e("Logging","calling load article in viewmodel")
        _uiState.value = ArticleUiState.Loading
       val articles =
                articleRepository.getArticles()
                    .map {
                        it.map {
                            it.toArticleData()
                                .toUiModel()
                        }
                    }.cachedIn(viewModelScope)
   //     Log.e("Logging","calling load article in viewmodel")
        _uiState.value = ArticleUiState.Success(articles)
            }
}
