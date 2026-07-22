package com.aris.greekcitizenshipquiz.ui.state

sealed class QuizUiState {

    data object Idle : QuizUiState()

    data object Loading : QuizUiState()

    data object Success : QuizUiState()

    data class Error(
        val message: String
    ) : QuizUiState()

    data object NoUpdates : QuizUiState()

}