package com.aris.greekcitizenshipquiz.ui.state

import com.aris.greekcitizenshipquiz.domain.model.Question

sealed interface QuestionsUiState {

    data object Loading : QuestionsUiState


    data class Success(
        val questions: List<Question>
    ) : QuestionsUiState


    data class Empty(
        val message: String
    ) : QuestionsUiState


    data class Error(
        val message: String
    ) : QuestionsUiState
}