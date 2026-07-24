package com.aris.greekcitizenshipquiz.domain.usecase

import com.aris.greekcitizenshipquiz.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveIncorrectAnswerCountUseCase @Inject constructor(
    private val repository: QuizRepository
) {

    operator fun invoke(): Flow<Int> {
        return repository.observeIncorrectAnswers()
            .map { it.size }
    }
}