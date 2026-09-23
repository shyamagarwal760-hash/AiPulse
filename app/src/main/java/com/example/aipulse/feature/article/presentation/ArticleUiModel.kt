package com.example.aipulse.feature.article.presentation

import com.example.aipulse.data.article.Domain.ArticleData
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

data class ArticleUiModel(
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    val date: String? = null,
)

fun ArticleData.toUiModel(): ArticleUiModel {
    return ArticleUiModel(
        author = author,
        title = title,
        description = description,
        imageUrl = imageUrl,
        date = formatArticleDate(date),
    )
}

private fun formatArticleDate(rawDate: String?): String? {
    if (rawDate.isNullOrBlank()) return null

    return runCatching {
        val articleDate = Instant.parse(rawDate)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        val today = LocalDate.now(ZoneId.systemDefault())
        val daysAgo = java.time.temporal.ChronoUnit.DAYS.between(articleDate, today).toInt()

        when (daysAgo) {
            0 -> "Today"
            1 -> "Yesterday"
            else -> "$daysAgo days ago"
        }
    }.getOrNull() ?: rawDate
}
