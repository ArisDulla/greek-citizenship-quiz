package com.aris.greekcitizenshipquiz.domain.usecase

import com.aris.greekcitizenshipquiz.domain.model.SyncResult
import com.aris.greekcitizenshipquiz.domain.repository.QuizRepository
import javax.inject.Inject


class SyncQuizDataUseCase @Inject constructor(
    private val repository: QuizRepository
) {


    suspend operator fun invoke(): SyncResult {

        return repository.syncQuizData()

    }

}