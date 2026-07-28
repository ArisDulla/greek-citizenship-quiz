package com.aris.greekcitizenshipquiz.domain.usecase

import com.aris.greekcitizenshipquiz.domain.model.Question
import com.aris.greekcitizenshipquiz.domain.repository.QuestionRepository
import javax.inject.Inject

class GetQuestionsByCategoryAndTypeUseCase @Inject constructor(
    private val repository: QuestionRepository
) {

    suspend operator fun invoke(
        categoryId: Int,
        typeQuestionId: Int
    ): List<Question> {

        return repository.getQuestionsByCategoryAndType(
            categoryId = categoryId,
            typeQuestionId = typeQuestionId
        )
    }
}