package com.aris.greekcitizenshipquiz.data.mapper

import com.aris.greekcitizenshipquiz.data.local.entity.SyncMetaEntity
import com.aris.greekcitizenshipquiz.data.remote.dto.VersionDto


fun VersionDto.toEntity(): SyncMetaEntity {

    return SyncMetaEntity(

        version = version,

        updatedAt = updatedAt
    )
}