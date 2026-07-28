package com.aris.greekcitizenshipquiz.data.repository

import com.aris.greekcitizenshipquiz.data.local.dao.IncorrectAnswerDao
import com.aris.greekcitizenshipquiz.data.local.entity.IncorrectAnswerEntity
import com.aris.greekcitizenshipquiz.domain.repository.IncorrectAnswerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IncorrectAnswerRepositoryImpl @Inject constructor(
    private val incorrectAnswerDao: IncorrectAnswerDao
) : IncorrectAnswerRepository {

    override suspend fun add(
        questionId: Int
    ) {
        incorrectAnswerDao.insert(
            IncorrectAnswerEntity(questionId)
        )
    }

    override fun observeAllQuestionIds(): Flow<List<Int>> {

        return incorrectAnswerDao
            .observeAll()
            .map { answers ->

                answers.map { it.questionId }
            }
    }

    override suspend fun remove(
        questionId: Int
    ) {
        incorrectAnswerDao.delete(questionId)
    }

    override suspend fun removeAll() {
        incorrectAnswerDao.deleteAll()
    }

    override fun observeCount(): Flow<Int> {

        return incorrectAnswerDao.observeCount()
    }
}