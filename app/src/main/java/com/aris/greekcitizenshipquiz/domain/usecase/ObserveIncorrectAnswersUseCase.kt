package com.aris.greekcitizenshipquiz.domain.usecase

import com.aris.greekcitizenshipquiz.domain.repository.IncorrectAnswerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveIncorrectAnswersUseCase @Inject constructor(
    private val repository: IncorrectAnswerRepository
) {

    operator fun invoke(): Flow<List<Int>> {
        return repository.observeAllQuestionIds()
    }
}