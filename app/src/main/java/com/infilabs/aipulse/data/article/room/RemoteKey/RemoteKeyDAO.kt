package com.infilabs.aipulse.data.article.room.RemoteKey

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeyDAO {
    @Query("SELECT * FROM remote_keys WHERE articleUrl = :articleUrl")
    suspend fun getRemoteKey(articleUrl : String?) : RemoteKeysEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeysEntity: List<RemoteKeysEntity>)

    @Query("Delete from remote_keys")
    suspend fun clearRemoteKeys()
}