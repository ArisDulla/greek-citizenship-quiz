package com.aris.greekcitizenshipquiz.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aris.greekcitizenshipquiz.domain.usecase.SyncQuizDataUseCase
import com.aris.greekcitizenshipquiz.ui.state.QuizUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val syncQuizDataUseCase: SyncQuizDataUseCase
) : ViewModel() {


    private val _uiState =
        MutableStateFlow<QuizUiState>(
            QuizUiState.Idle
        )


    val uiState: StateFlow<QuizUiState> =
        _uiState.asStateFlow()

    fun syncQuizData() {


        viewModelScope.launch {


            _uiState.value =
                QuizUiState.Loading


            val result =
                syncQuizDataUseCase()


            _uiState.value =
                if (result.isSuccess) {

                    QuizUiState.Success

                } else {

                    QuizUiState.Error(
                        result.exceptionOrNull()
                            ?.message
                            ?: "Unknown error"
                    )

                }
        }
    }

}