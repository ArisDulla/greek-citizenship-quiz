package com.aris.greekcitizenshipquiz.domain.usecase

import com.aris.greekcitizenshipquiz.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveIncorrectAnswersUseCase @Inject constructor(
    private val repository: QuizRepository
) {

    operator fun invoke(): Flow<List<Int>> {
        return repository.observeIncorrectAnswers()
    }
}