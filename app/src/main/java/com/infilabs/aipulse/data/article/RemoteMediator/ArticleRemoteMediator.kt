package com.infilabs.aipulse.data.article.RemoteMediator

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.infilabs.aipulse.data.article.Mapper.toArticleEntity
import com.infilabs.aipulse.data.article.remote.ArticleApi
import com.infilabs.aipulse.data.article.room.ArticleDatabase
import com.infilabs.aipulse.data.article.room.ArticleEntity
import com.infilabs.aipulse.data.article.room.RemoteKey.RemoteKeysEntity
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator(
    private val articleApi: ArticleApi,
    private val articledb : ArticleDatabase
) : RemoteMediator<Int, ArticleEntity>() {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        val page = when(loadType){
            LoadType.REFRESH -> {
                1
            }

            LoadType.APPEND ->{
                return MediatorResult.Success(
                    endOfPaginationReached = true
                )
            }
            LoadType.PREPEND -> {
                val lastArticle = state.lastItemOrNull() ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                val remoteKeys = articledb
                    .remoteKeysDao()
                    .getRemoteKey(lastArticle.url)

                remoteKeys?.nextPage
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
            }
        }

        return try {
            val response = articleApi.getArticles(
                page = page,
                pageSize = 20
            )
            Log.e("Logging","calling load article in viewmodel")

            val listofArtcileEntities = response.articles.mapNotNull { it ->
                it.toArticleEntity()
            }

            val endofPaginationReached = listofArtcileEntities.isEmpty() ||
                    page * state.config.pageSize >= response!!.totalResults!!.toInt()

            val prevKey = if (page == 1) null else page - 1

            val nextKey = if (endofPaginationReached) null else page + 1

            val remoteKeys = listofArtcileEntities.map { article ->
                RemoteKeysEntity(
                    articleUrl = article.url,
                    nextKey,
                    prevKey
                )
            }

            articledb.withTransaction {
                if(LoadType.REFRESH == loadType){
                    articledb.remoteKeysDao().clearRemoteKeys()
                    articledb.articleDao().clearRoom()
                }

                articledb.remoteKeysDao().insertAll(remoteKeys)
                articledb.articleDao().insertArticles(listofArtcileEntities)
                Log.e("Database Logging","insertion of data to room database is complete")
            }

            MediatorResult.Success(
                endOfPaginationReached = endofPaginationReached
            )
        }catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }

    }
}