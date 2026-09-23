package com.infilabs.aipulse.feature.article.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.Image
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.infilabs.aipulse.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(
    uiState: ArticleUiState,
    summaryUiState: ArticleSummaryUiState,
    onSummarizeClick: (List<ArticleUiModel>) -> Unit,
    onDismissSummary: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Article",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            )

            when (uiState) {
                ArticleUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is ArticleUiState.Success -> {
                    val articles = uiState.articles.collectAsLazyPagingItems()
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        items(count = articles.itemCount) { index ->
                            val article = articles[index]
                            article?.let {
                                ArticleItem(article = article)
                            }
                        }
                    }
                }

                is ArticleUiState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        AsyncImage(
                            model = R.drawable.ic_launcher_foreground,
                            contentDescription = "Error illustration",
                            modifier = Modifier.height(180.dp),
                            contentScale = ContentScale.Fit,
                            placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Something went wrong, please try again later",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }
        }

        if (uiState is ArticleUiState.Success) {
            val articles = uiState.articles.collectAsLazyPagingItems()

            val loadedArticles = buildList {
                for (index in 0 until articles.itemCount) {
                    articles[index]?.let { add(it) }
                }
            }
            FloatingActionButton(
                onClick = { onSummarizeClick(loadedArticles) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp),
            ) {
                Image(
                    painter = painterResource(R.drawable.ai_pulse_logo),
                    contentDescription = "Generate AI summary",
                    modifier = Modifier.height(34.dp),
                )
            }
        }

        when (val state = summaryUiState) {
            ArticleSummaryUiState.Idle -> Unit
            ArticleSummaryUiState.Loading -> {
                ModalBottomSheet(onDismissRequest = {}) {
                    SummaryLoadingContent()
                }
            }
            is ArticleSummaryUiState.Success -> {
                ModalBottomSheet(onDismissRequest = onDismissSummary) {
                    SummaryContent(summary = state.summary, onDismiss = onDismissSummary)
                }
            }
            is ArticleSummaryUiState.Error -> {
                AlertDialog(
                    onDismissRequest = onDismissSummary,
                    title = { Text("Summary unavailable") },
                    text = { Text("Something went wrong while creating the summary. Please try again later.") },
                    confirmButton = {
                        androidx.compose.material3.TextButton(onClick = onDismissSummary) {
                            Text("Close")
                        }
                    },
                )
            }
        }
    }
}

@Composable
private fun SummaryLoadingContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        CircularProgressIndicator()
        Text("Creating your article summary...")
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun SummaryContent(
    summary: com.infilabs.aipulse.data.ai.AiSummary,
    onDismiss: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("AI Summary", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Card(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = summary.summary,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        Text(
            text = "Sentiment: ${summary.sentiment.replaceFirstChar { it.uppercase() }}",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        androidx.compose.material3.TextButton(
            onClick = onDismiss,
            modifier = Modifier.align(Alignment.End),
        ) {
            Text("Done")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun ArticleItem(
    article: ArticleUiModel,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        tonalElevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = article.imageUrl,
                contentDescription = article.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(MaterialTheme.shapes.medium),
            )
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = article.title.orEmpty(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = article.description.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = article.date.orEmpty(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.End),
                )
            }
        }
    }
}
