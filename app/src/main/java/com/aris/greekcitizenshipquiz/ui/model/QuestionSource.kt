package com.aris.greekcitizenshipquiz.ui.model

sealed interface QuestionSource {

    data object Incorrect : QuestionSource

    data object NewQuestions : QuestionSource


    data class Category(
        val categoryId: Int
    ) : QuestionSource


    data class CategoryType(
        val categoryId: Int,
        val typeQuestionId: Int
    ) : QuestionSource
}