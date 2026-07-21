package com.aris.greekcitizenshipquiz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aris.greekcitizenshipquiz.data.local.entity.SyncMetaEntity

@Dao
interface SyncMetaDao {


    @Query("""
        SELECT *
        FROM sync_meta
        WHERE id = 1
    """)
    suspend fun getSyncMeta(): SyncMetaEntity?


    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun saveSyncMeta(
        syncMeta: SyncMetaEntity
    )


    @Query("""
        UPDATE sync_meta
        SET version = :version,
            updatedAt = :updatedAt
        WHERE id = 1
    """)
    suspend fun updateSyncVersion(
        version: Int,
        updatedAt: String
    )


    @Query("""
        DELETE FROM sync_meta
    """)
    suspend fun clearSyncMeta()
}