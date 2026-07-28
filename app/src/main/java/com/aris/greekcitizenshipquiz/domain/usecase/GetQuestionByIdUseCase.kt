package com.aris.greekcitizenshipquiz.domain.usecase

import com.aris.greekcitizenshipquiz.domain.model.Question
import com.aris.greekcitizenshipquiz.domain.repository.QuestionRepository
import javax.inject.Inject

class GetQuestionByIdUseCase @Inject constructor(
    private val repository: QuestionRepository
) {

    suspend operator fun invoke(
        questionId: Int
    ): Question? {

        return repository.getQuestionById(questionId)
    }
}