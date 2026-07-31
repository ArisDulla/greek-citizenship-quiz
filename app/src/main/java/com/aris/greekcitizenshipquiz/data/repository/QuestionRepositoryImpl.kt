package com.aris.greekcitizenshipquiz.data.repository

import com.aris.greekcitizenshipquiz.data.local.dao.QuestionDao
import com.aris.greekcitizenshipquiz.data.mapper.toDomain
import com.aris.greekcitizenshipquiz.domain.model.Question
import com.aris.greekcitizenshipquiz.domain.repository.QuestionRepository
import javax.inject.Inject
import com.aris.greekcitizenshipquiz.domain.model.CategoryTypeCount

class QuestionRepositoryImpl @Inject constructor(
    private val questionDao: QuestionDao
) : QuestionRepository {

    override suspend fun getQuestionsByCategory(
        categoryId: Int
    ): List<Question> {

        return questionDao
            .getQuestionsByCategory(categoryId)
            .map { it.toDomain() }

    }

    override suspend fun getQuestionsByCategoryAndType(
        categoryId: Int,
        typeQuestionId: Int
    ): List<Question> {

        return questionDao
            .getQuestionsByCategoryAndType(categoryId, typeQuestionId)
            .map { it.toDomain() }
    }

    override suspend fun getRandomQuestionsByCategory(
        categoryId: Int,
        limit: Int
    ): List<Question> {

        return questionDao
            .getRandomQuestionsByCategory(categoryId, limit)
            .map { it.toDomain() }
    }

    override suspend fun getQuestionGroupCountByCategory(
        categoryId: Int
    ): Int {

        return questionDao.getQuestionGroupCountByCategory(categoryId)
    }

    override suspend fun getQuestionsByCategoryAndNew(): List<Question> {
        return questionDao
            .getQuestionsByCategoryAndNew()
            .map { it.toDomain() }
    }

    override suspend fun getQuestionTypesByCategory(
        categoryId: Int
    ): List<CategoryTypeCount> {

        return questionDao.getQuestionTypesByCategory(categoryId)
    }

}