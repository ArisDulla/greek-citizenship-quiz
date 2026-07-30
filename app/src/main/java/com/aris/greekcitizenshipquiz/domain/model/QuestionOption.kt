package com.aris.greekcitizenshipquiz.domain.model

data class QuestionOption(

    val optionId: Int,

    val optionText: String?,

    val optionImage: String?,

    val isCorrect: Boolean,

    val order: Int
)