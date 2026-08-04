package com.aris.greekcitizenshipquiz.domain.usecase
import com.aris.greekcitizenshipquiz.domain.model.Question
import com.aris.greekcitizenshipquiz.domain.repository.IncorrectAnswerRepository
import javax.inject.Inject

class ObserveIncorrectQuestionsUseCase @Inject constructor(
    private val repository: IncorrectAnswerRepository
) {

    suspend operator fun invoke(): List<Question> {
        return repository.observeIncorrectQuestions()
    }
}