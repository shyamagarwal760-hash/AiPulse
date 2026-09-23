package com.infilabs.aipulse

import com.infilabs.aipulse.data.ai.AiSummary
import com.infilabs.aipulse.data.ai.AiSummaryRepository
import com.infilabs.aipulse.feature.article.presentation.ArticleSummaryUiState
import com.infilabs.aipulse.feature.article.presentation.ArticleSummaryViewModel
import com.infilabs.aipulse.feature.article.presentation.ArticleUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ArticleSummaryViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun summarize_emitsSuccessWithRepositorySummary() = runTest(testDispatcher) {
        val repository = FakeAiSummaryRepository(
            Result.success(AiSummary("A concise summary", "positive")),
        )
        val viewModel = ArticleSummaryViewModel(repository)

        viewModel.summarize(listOf(ArticleUiModel(title = "AI news")))
        advanceUntilIdle()

        assertEquals(
            ArticleSummaryUiState.Success(AiSummary("A concise summary", "positive")),
            viewModel.uiState.value,
        )
        assertEquals(listOf("AI news"), repository.receivedTitles)
    }

    private class FakeAiSummaryRepository(
        private val result: Result<AiSummary>,
    ) : AiSummaryRepository {
        var receivedTitles: List<String> = emptyList()

        override suspend fun getAiSummary(title: List<String>): Result<AiSummary> {
            receivedTitles = title
            return result
        }
    }
}
