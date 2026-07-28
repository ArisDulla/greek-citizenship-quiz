package com.aris.greekcitizenshipquiz.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aris.greekcitizenshipquiz.domain.usecase.GetActiveCategoriesWithQuestionsUseCase
import com.aris.greekcitizenshipquiz.domain.usecase.ObserveIncorrectAnswerCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class QuizMenuViewModel @Inject constructor(
    private val observeIncorrectAnswerCountUseCase: ObserveIncorrectAnswerCountUseCase,
    private val getActiveCategoriesWithQuestionsUseCase: GetActiveCategoriesWithQuestionsUseCase
) : ViewModel() {


    val incorrectCount: StateFlow<Int> =
        observeIncorrectAnswerCountUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = 0
            )

    val categories =
        getActiveCategoriesWithQuestionsUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )
}