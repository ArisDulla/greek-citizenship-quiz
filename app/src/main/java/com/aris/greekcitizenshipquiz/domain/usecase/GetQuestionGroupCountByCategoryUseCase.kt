package com.aris.greekcitizenshipquiz.domain.usecase

import com.aris.greekcitizenshipquiz.domain.repository.QuestionRepository
import javax.inject.Inject

class GetQuestionGroupCountByCategoryUseCase @Inject constructor(
    private val repository: QuestionRepository
) {

    suspend operator fun invoke(
        categoryId: Int
    ): Int {

        return repository.getQuestionGroupCountByCategory(categoryId)
    }
}