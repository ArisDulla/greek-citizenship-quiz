package com.aris.greekcitizenshipquiz.domain.model

data class Question(

    val questionId: Int,

    val topic: String,

    val introText: String?,

    val mainText: String,

    val textCompletion: String?,

    val focusCompletion: String?,

    val isNew: Boolean,

    val maxCorrect: Int?,

    val categoryId: Int,

    val typeQuestionId: Int?,

    val questionNumber: Int,

    val imageAnswer: String?

)