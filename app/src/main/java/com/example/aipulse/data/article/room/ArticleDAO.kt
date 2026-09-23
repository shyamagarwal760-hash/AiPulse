package com.example.aipulse.data.article.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ArticleDAO {
    @Query("""
        SELECT * FROM articles 
        ORDER BY date DESC
        """)
    fun getArticles(): PagingSource<Int, ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticles(
        articles: List<ArticleEntity>
    ): List<Long>

    @Query("DELETE FROM articles")
    suspend fun clearRoom()
}