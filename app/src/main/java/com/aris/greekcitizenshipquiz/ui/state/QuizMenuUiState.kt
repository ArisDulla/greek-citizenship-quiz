package com.aris.greekcitizenshipquiz.ui.state

import com.aris.greekcitizenshipquiz.domain.model.CategoryQuestion

sealed interface QuizMenuUiState {

    data object Loading : QuizMenuUiState

    data class Success(
        val categories: List<CategoryQuestion>
    ) : QuizMenuUiState

    data class Error(
        val message: String
    ) : QuizMenuUiState
}