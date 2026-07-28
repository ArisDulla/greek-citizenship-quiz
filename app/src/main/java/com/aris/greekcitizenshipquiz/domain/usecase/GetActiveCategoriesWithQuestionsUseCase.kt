package com.aris.greekcitizenshipquiz.domain.usecase

import com.aris.greekcitizenshipquiz.data.local.entity.CategoryQuestionEntity
import com.aris.greekcitizenshipquiz.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActiveCategoriesWithQuestionsUseCase @Inject constructor(
    private val quizRepository: QuizRepository
) {

    operator fun invoke(): Flow<List<CategoryQuestionEntity>> {
        return quizRepository.getActiveCategoriesWithQuestions()
    }
}