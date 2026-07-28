package com.aris.greekcitizenshipquiz.domain.usecase

import com.aris.greekcitizenshipquiz.domain.model.CategoryQuestion
import com.aris.greekcitizenshipquiz.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActiveCategoriesWithQuestionsUseCase @Inject constructor(
    private val quizRepository: CategoryRepository
) {

    operator fun invoke(): Flow<List<CategoryQuestion>> {
        return quizRepository.getActiveCategoriesWithQuestions()
    }
}