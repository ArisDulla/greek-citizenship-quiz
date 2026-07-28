package com.aris.greekcitizenshipquiz.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aris.greekcitizenshipquiz.domain.usecase.SyncQuizDataUseCase
import com.aris.greekcitizenshipquiz.ui.state.QuizUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.asStateFlow
import com.aris.greekcitizenshipquiz.domain.model.SyncResult
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import com.aris.greekcitizenshipquiz.domain.usecase.ObserveExamPeriodTitleUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val syncQuizDataUseCase: SyncQuizDataUseCase,
    observeExamPeriodTitleUseCase: ObserveExamPeriodTitleUseCase

) : ViewModel() {

    companion object {
        private const val STOP_TIMEOUT_MILLIS = 5_000L
        private val LOADING_DELAY = 1_000.milliseconds
    }

    private val _uiState =
        MutableStateFlow<QuizUiState>(
            QuizUiState.Idle
        )

    val uiState: StateFlow<QuizUiState> =
        _uiState.asStateFlow()

    val examPeriodTitle: StateFlow<String?> =
        observeExamPeriodTitleUseCase()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
                null
            )

    fun syncQuizData() {

        viewModelScope.launch {

            _uiState.value = QuizUiState.Loading

            val result = syncQuizDataUseCase()

            delay(LOADING_DELAY)

            _uiState.value = when (result) {

                SyncResult.Updated -> {
                    QuizUiState.Success
                }

                SyncResult.NoUpdates -> {
                    QuizUiState.NoUpdates
                }

                is SyncResult.Error -> {
                    QuizUiState.Error("Αδυναμία σύνδεσης")
                }
            }
            delay(7.seconds)
            _uiState.value = QuizUiState.Idle
        }
    }

}