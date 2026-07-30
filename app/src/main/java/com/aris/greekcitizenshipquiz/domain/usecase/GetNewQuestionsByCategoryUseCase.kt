package com.aris.greekcitizenshipquiz.domain.usecase

import com.aris.greekcitizenshipquiz.domain.repository.QuestionRepository
import javax.inject.Inject
import com.aris.greekcitizenshipquiz.domain.model.Question

class GetNewQuestionsByCategoryUseCase @Inject constructor(
    private val repository: QuestionRepository
) {
    suspend operator fun invoke(): List<Question> {
        return repository.getQuestionsByCategoryAndNew()
    }
}