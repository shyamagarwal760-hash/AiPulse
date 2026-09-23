package com.infilabs.aipulse.data.source.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SourceDAO {
    @Query("Select * from sources order by name ASC")
    fun getSources() : Flow<List<SourceEntity>>

    @Insert
    suspend fun insertAll(sources : List<SourceEntity>)

    @Query("Delete from sources")
    suspend fun clearSources()

}