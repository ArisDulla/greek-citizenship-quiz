package com.aris.greekcitizenshipquiz.domain.usecase

import com.aris.greekcitizenshipquiz.domain.model.Question
import com.aris.greekcitizenshipquiz.domain.repository.QuestionRepository
import javax.inject.Inject

class GetQuestionsByCategoryUseCase @Inject constructor(
    private val repository: QuestionRepository
) {

    suspend operator fun invoke(
        categoryId: Int
    ): List<Question> {

        return repository.getQuestionsByCategory(categoryId)
    }
}