package com.aris.greekcitizenshipquiz.data.remote.dto

import com.squareup.moshi.Json


data class QuestionDto(

    @param:Json(name = "questionId")
    val questionId: Int,

    @param:Json(name = "topic")
    val topic: String,

    @param:Json(name = "intro_text")
    val introText: String?,

    @param:Json(name = "main_text")
    val mainText: String,

    @param:Json(name = "text_completion")
    val textCompletion: String?,

    @param:Json(name = "focus_completion")
    val focusCompletion: String?,

    @param:Json(name = "is_new")
    val isNew: Boolean,

    @param:Json(name = "max_correct")
    val maxCorrect: Int?,

    @param:Json(name = "category")
    val categoryId: Int,

    @param:Json(name = "type_question")
    val typeQuestionId: Int?,

    @param:Json(name = "question_number")
    val questionNumber: Int,

    @param:Json(name = "is_deleted")
    val isDeleted: Boolean,

    @param:Json(name = "image_answer")
    val imageAnswer: String?,

    @param:Json(name = "updated_at")
    val updatedAt: String
)