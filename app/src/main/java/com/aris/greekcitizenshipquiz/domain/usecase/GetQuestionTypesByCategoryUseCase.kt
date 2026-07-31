package com.aris.greekcitizenshipquiz.domain.usecase

import com.aris.greekcitizenshipquiz.domain.model.CategoryTypeCount
import com.aris.greekcitizenshipquiz.domain.repository.QuestionRepository
import javax.inject.Inject

class GetQuestionTypesByCategoryUseCase @Inject constructor(
    private val repository: QuestionRepository
) {

    suspend operator fun invoke(
        categoryId: Int
    ): List<CategoryTypeCount> {

        return repository.getQuestionTypesByCategory(categoryId)
    }
}