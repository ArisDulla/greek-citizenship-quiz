package com.aris.greekcitizenshipquiz.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sync_meta",

    indices = [
        Index(
            value = ["version"],
            unique = true
        )
    ]
)
data class SyncMetaEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val version: Int,
    val updatedAt: String
)