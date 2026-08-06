package com.aris.greekcitizenshipquiz.ui.model

sealed interface QuestionSource {

    val isIncorrectMode: Boolean
        get() = false


    data object Incorrect : QuestionSource {
        override val isIncorrectMode = true
    }


    data object NewQuestions : QuestionSource

    data object RandomTest : QuestionSource


    data class Category(
        val categoryId: Int
    ) : QuestionSource


    data class CategoryType(
        val categoryId: Int,
        val typeQuestionId: Int
    ) : QuestionSource
}