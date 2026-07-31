package com.aris.greekcitizenshipquiz.domain.model

data class CategoryTypeCount(
    val typeQuestionId: Int,
    val typeName: String,
    val description: String?,
    val sortOrder: Int,
    val isActive: Boolean,
    val updatedAt: String,
    val count: Int
)