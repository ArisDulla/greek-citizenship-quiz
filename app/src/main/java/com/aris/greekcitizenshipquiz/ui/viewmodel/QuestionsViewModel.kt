package com.aris.greekcitizenshipquiz.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aris.greekcitizenshipquiz.domain.usecase.GetQuestionsByCategoryAndTypeUseCase
import com.aris.greekcitizenshipquiz.domain.usecase.GetQuestionsByCategoryUseCase
import com.aris.greekcitizenshipquiz.domain.usecase.GetNewQuestionsByCategoryUseCase
import com.aris.greekcitizenshipquiz.domain.usecase.ObserveIncorrectQuestionsUseCase
import com.aris.greekcitizenshipquiz.ui.model.QuestionSource
import com.aris.greekcitizenshipquiz.ui.state.QuestionsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(

    private val observeIncorrectQuestionsUseCase: ObserveIncorrectQuestionsUseCase,

    private val getQuestionsByNewUseCase: GetNewQuestionsByCategoryUseCase,

    private val getQuestionsByCategoryUseCase: GetQuestionsByCategoryUseCase,

    private val getQuestionsByCategoryAndTypeUseCase: GetQuestionsByCategoryAndTypeUseCase,


) : ViewModel() {

    private var totalQuestions = 0
    private val _uiState =
        MutableStateFlow<QuestionsUiState>(
            QuestionsUiState.Loading
        )

    val uiState =
        _uiState.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)

    val currentIndex = _currentIndex.asStateFlow()

    fun nextQuestion() {

        if (_currentIndex.value < totalQuestions - 1) {
            _currentIndex.value++
        }

    }

    fun loadQuestions(
        source: QuestionSource
    ) {

        _currentIndex.value = 0
        totalQuestions = 0


        viewModelScope.launch {

            try {

                _uiState.value =
                    QuestionsUiState.Loading


                val questions = when(source) {


                    QuestionSource.Incorrect -> {

                        observeIncorrectQuestionsUseCase()
                    }


                    QuestionSource.NewQuestions -> {

                        getQuestionsByNewUseCase()
                    }


                    is QuestionSource.Category -> {

                        getQuestionsByCategoryUseCase(
                            source.categoryId
                        )
                    }


                    is QuestionSource.CategoryType -> {

                        getQuestionsByCategoryAndTypeUseCase(
                            categoryId = source.categoryId,
                            typeQuestionId = source.typeQuestionId
                        )
                    }
                }

                totalQuestions = questions.size

                if (questions.isEmpty()) {

                    _uiState.value =
                        QuestionsUiState.Empty(
                            message = "Δεν βρέθηκαν ερωτήσεις"
                        )

                } else {

                    _uiState.value =
                        QuestionsUiState.Success(
                            questions
                        )
                }


            } catch (e: Exception) {


                _uiState.value =
                    QuestionsUiState.Error(
                        e.message ?: "Άγνωστο σφάλμα"
                    )
            }
        }
    }
}