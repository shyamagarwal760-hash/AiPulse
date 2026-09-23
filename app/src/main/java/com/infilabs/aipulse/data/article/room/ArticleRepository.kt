package com.infilabs.aipulse.data.article.room

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
     fun getArticles(): Flow<PagingData<ArticleEntity>>
}
