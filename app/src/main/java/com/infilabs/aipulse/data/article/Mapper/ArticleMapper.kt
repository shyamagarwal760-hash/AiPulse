package com.infilabs.aipulse.data.article.Mapper

import com.infilabs.aipulse.data.article.Domain.ArticleData
import com.infilabs.aipulse.data.article.remote.ArticleItemDto
import com.infilabs.aipulse.data.article.room.ArticleEntity

fun ArticleItemDto.toArticleEntity() : ArticleEntity?{
    val articleUrl = url ?: return null

    return ArticleEntity(
        url = articleUrl,
        author = this.author,
        title = this.title,
        description = this.description,
        imageUrl = this.imageUrl,
        date = this.date
    )
}

fun ArticleEntity.toArticleData() : ArticleData{
    return ArticleData(
        author = this.author,
        title = this.title,
        description = this.description,
        imageUrl = this.imageUrl,
        date = this.date
    )
}
