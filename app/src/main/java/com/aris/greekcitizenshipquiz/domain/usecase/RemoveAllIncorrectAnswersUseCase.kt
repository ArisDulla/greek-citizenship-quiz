package com.aris.greekcitizenshipquiz.domain.usecase

import com.aris.greekcitizenshipquiz.domain.repository.IncorrectAnswerRepository
import javax.inject.Inject

class RemoveAllIncorrectAnswersUseCase @Inject constructor(
    private val repository: IncorrectAnswerRepository
) {

    suspend operator fun invoke() {
        repository.removeAll()
    }
}