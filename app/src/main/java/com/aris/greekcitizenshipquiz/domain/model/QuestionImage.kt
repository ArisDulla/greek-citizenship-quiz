package com.aris.greekcitizenshipquiz.domain.model

data class QuestionImage(

    val imageId: Int,

    val image: String?,

    val caption: String?,

    val order: Int
)