package com.aris.greekcitizenshipquiz.domain.model

data class CategoryQuestion(
    val categoryId: Int,
    val name: String,
    val description: String?
)