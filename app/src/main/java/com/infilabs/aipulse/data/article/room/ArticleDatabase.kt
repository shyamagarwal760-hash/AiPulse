package com.infilabs.aipulse.data.article.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.infilabs.aipulse.data.article.room.RemoteKey.RemoteKeyDAO
import com.infilabs.aipulse.data.article.room.RemoteKey.RemoteKeysEntity
import com.infilabs.aipulse.data.source.Room.SourceDAO
import com.infilabs.aipulse.data.source.Room.SourceEntity

@Database(entities = [
    ArticleEntity::class,
    RemoteKeysEntity::class,
    SourceEntity::class
                     ], version = 3, exportSchema = true)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDAO

    abstract fun remoteKeysDao(): RemoteKeyDAO

    abstract fun sourceDao() : SourceDAO
}
