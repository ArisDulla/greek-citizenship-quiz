package com.aris.greekcitizenshipquiz.domain.usecase

import com.aris.greekcitizenshipquiz.domain.repository.IncorrectAnswerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveIncorrectAnswerCountUseCase @Inject constructor(
    private val repository: IncorrectAnswerRepository
) {

    operator fun invoke(): Flow<Int> {
        return repository.observeCount()
    }
}