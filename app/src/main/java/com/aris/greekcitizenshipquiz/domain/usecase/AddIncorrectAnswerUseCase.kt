package com.aris.greekcitizenshipquiz.domain.usecase

import com.aris.greekcitizenshipquiz.domain.repository.QuizRepository
import javax.inject.Inject

class AddIncorrectAnswerUseCase @Inject constructor(
    private val repository: QuizRepository
) {

    suspend operator fun invoke(questionId: Int) {
        repository.addIncorrectAnswer(questionId)
    }
}