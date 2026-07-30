package com.aris.greekcitizenshipquiz.domain.repository

import com.aris.greekcitizenshipquiz.domain.model.Question

interface QuestionRepository {

    suspend fun getRandomQuestionsByCategory(
        categoryId: Int,
        limit: Int
    ): List<Question>

    suspend fun getQuestionGroupCountByCategory(
        categoryId: Int
    ): Int

    suspend fun getQuestionsByCategory(
        categoryId: Int
    ): List<Question>

    suspend fun getQuestionsByCategoryAndType(
        categoryId: Int,
        typeQuestionId: Int
    ): List<Question>
}