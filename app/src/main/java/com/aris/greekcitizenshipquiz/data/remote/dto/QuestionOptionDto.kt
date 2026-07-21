package com.aris.greekcitizenshipquiz.data.remote.dto

import com.squareup.moshi.Json


data class QuestionOptionDto(

    @param:Json(name = "optionId")
    val optionId: Int,

    @param:Json(name = "question")
    val questionId: Int,

    @param:Json(name = "option_text")
    val optionText: String?,

    @param:Json(name = "option_image")
    val optionImage: String?,

    @param:Json(name = "is_correct")
    val isCorrect: Boolean,

    @param:Json(name = "order")
    val order: Int,

    @param:Json(name = "updated_at")
    val updatedAt: String
)