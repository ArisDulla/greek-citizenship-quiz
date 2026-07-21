package com.aris.greekcitizenshipquiz.data.remote.dto

import com.squareup.moshi.Json


data class TypeQuestionDto(

    @param:Json(name = "typeQuestionId")
    val typeQuestionId: Int,

    @param:Json(name = "name")
    val name: String,

    @param:Json(name = "description")
    val description: String?,

    @param:Json(name = "sort_order")
    val sortOrder: Int,

    @param:Json(name = "is_active")
    val isActive: Boolean,

    @param:Json(name = "updated_at")
    val updatedAt: String
)