package com.aris.greekcitizenshipquiz.data.remote.dto

import com.squareup.moshi.Json


data class ExamPeriodDto(

    @param:Json(name = "id")
    val id: Int,

    @param:Json(name = "title")
    val title: String,

    @param:Json(name = "is_active")
    val isActive: Boolean,

    @param:Json(name = "updated_at")
    val updatedAt: String
)