package com.example.aipulse.feature.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.paging.PagingData
import com.example.aipulse.R
import com.example.aipulse.feature.article.presentation.ArticleScreen
import com.example.aipulse.feature.article.presentation.ArticleSummaryUiState
import com.example.aipulse.feature.article.presentation.ArticleUiModel
import com.example.aipulse.feature.article.presentation.ArticleUiState
import com.example.aipulse.feature.source.presentation.SourceScreen
import com.example.aipulse.feature.source.presentation.SourceUiState
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeScreen(
    articleUiState: ArticleUiState,
    sourceUiState: SourceUiState,
    summaryUiState: ArticleSummaryUiState,
    onSummarizeClick: (List<ArticleUiModel>) -> Unit,
    onDismissSummary: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Filled.Article, contentDescription = "Article") },
                    label = { Text("Article") },
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Filled.Book, contentDescription = "Source") },
                    label = { Text("Source") },
                )
            }
        },
    ) { innerPadding ->
        when (selectedTab) {
            0 -> ArticleScreen(
                uiState = articleUiState,
                summaryUiState = summaryUiState,
                onSummarizeClick = onSummarizeClick,
                onDismissSummary = onDismissSummary,
                modifier = Modifier.padding(innerPadding),
            )
            else -> SourceScreen(
                uiState = sourceUiState,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            )
        }
    }
}
