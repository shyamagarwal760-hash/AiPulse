package com.example.aipulse.data.source.Room

import android.util.Log
import androidx.room.Database
import androidx.room.withTransaction
import com.example.aipulse.data.article.room.ArticleDatabase
import com.example.aipulse.data.source.Domain.SourceData
import com.example.aipulse.data.source.Mapper.toSourceData
import com.example.aipulse.data.source.Mapper.toSourceEntity
import com.example.aipulse.data.source.Remote.SourceApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SourceRepositoryImpl @Inject constructor(
    private val sourceApi: SourceApi,
    private val sourcedao: SourceDAO,
    private val database: ArticleDatabase
) : SourceRepository {
    override  fun observeSources(): Flow<List<SourceData>> {
        return sourcedao.getSources().map {
            it.map {
                it.toSourceData()
            }
        }
    }

    override suspend fun refreshData() {
        val response = sourceApi.getSources()
        Log.e("debugging","API sources: ${response.sources.size}")
        val sources = response.sources.mapNotNull {
            it.toSourceEntity()
        }
        Log.e("Debugging","size of cached sources ${sources.size}")
        database.withTransaction {
            sourcedao.clearSources()
            sourcedao.insertAll(sources)
        }
    }
}