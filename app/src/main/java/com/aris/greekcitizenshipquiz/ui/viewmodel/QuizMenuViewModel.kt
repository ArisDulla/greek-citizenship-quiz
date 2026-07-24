package com.aris.greekcitizenshipquiz.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aris.greekcitizenshipquiz.domain.usecase.ObserveIncorrectAnswerCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class QuizMenuViewModel @Inject constructor(
    observeIncorrectAnswerCountUseCase: ObserveIncorrectAnswerCountUseCase
) : ViewModel() {


    val incorrectCount =
        observeIncorrectAnswerCountUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = 0
            )
}