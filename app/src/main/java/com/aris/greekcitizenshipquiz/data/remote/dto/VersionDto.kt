package com.aris.greekcitizenshipquiz.data.remote.dto

import com.squareup.moshi.Json


data class VersionDto(

    @param:Json(name = "version")
    val version: Int,

    @param:Json(name = "updated_at")
    val updatedAt: String
)