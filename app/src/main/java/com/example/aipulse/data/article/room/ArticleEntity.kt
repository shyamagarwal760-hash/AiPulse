package com.example.aipulse.data.article.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "articles",
    indices = [
        Index(value = ["url"],unique = true)
    ])
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val url : String,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    val date: String? = null,
)
