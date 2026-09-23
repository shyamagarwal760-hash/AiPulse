package com.example.aipulse.data.source.Room

import com.example.aipulse.data.source.Domain.SourceData
import kotlinx.coroutines.flow.Flow

interface SourceRepository {
      fun observeSources(): Flow<List<SourceData>>

    suspend fun refreshData()
}