package com.infilabs.aipulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.infilabs.aipulse.feature.article.presentation.ArticleSummaryViewModel
import com.infilabs.aipulse.feature.article.presentation.ArticleViewModel
import com.infilabs.aipulse.feature.auth.presentation.AuthMode
import com.infilabs.aipulse.feature.home.presentation.HomeScreen
import com.infilabs.aipulse.feature.source.presentation.SourceViewModel
import com.infilabs.aipulse.ui.theme.AIPulseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIPulseTheme {
                var showHome by remember { mutableStateOf(false) }
                var authMode by remember { mutableStateOf(AuthMode.Login) }


                    val articleViewModel: ArticleViewModel = hiltViewModel()
                    val sourceViewModel: SourceViewModel = hiltViewModel()
                    val summaryViewModel: ArticleSummaryViewModel = hiltViewModel()
                    val articleUiState by articleViewModel.uiState.collectAsState()
                    val sourceUiState by sourceViewModel.uiState.collectAsStateWithLifecycle()
                    val summaryUiState by summaryViewModel.uiState.collectAsStateWithLifecycle()

                    HomeScreen(
                        articleUiState = articleUiState,
                        sourceUiState = sourceUiState,
                        summaryUiState = summaryUiState,
                        onSummarizeClick = summaryViewModel::summarize,
                        onDismissSummary = summaryViewModel::reset,
                    )

            }
        }
    }
}
