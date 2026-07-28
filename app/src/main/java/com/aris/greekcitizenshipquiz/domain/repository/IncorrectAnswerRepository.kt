package com.aris.greekcitizenshipquiz.domain.repository

import kotlinx.coroutines.flow.Flow

interface IncorrectAnswerRepository {

    suspend fun add(
        questionId: Int
    )

    fun observeAllQuestionIds(): Flow<List<Int>>

    suspend fun remove(
        questionId: Int
    )

    suspend fun removeAll()

    fun observeCount(): Flow<Int>
}