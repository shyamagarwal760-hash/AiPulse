package com.infilabs.aipulse.data.article.room

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.infilabs.aipulse.data.article.RemoteMediator.ArticleRemoteMediator
import com.infilabs.aipulse.data.article.remote.ArticleApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val articleDao: ArticleDAO,
    private val articleApi: ArticleApi,
    private val database: ArticleDatabase
) : ArticleRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getArticles(): Flow<PagingData<ArticleEntity>> {
        return Pager(
            config = PagingConfig(
                20,
                prefetchDistance = 5
            ),
            remoteMediator = ArticleRemoteMediator(
                articleApi = articleApi,
                articledb = database
            ),
            pagingSourceFactory = {
                articleDao.getArticles()
            }
        ).flow
    }
}