package com.aris.greekcitizenshipquiz.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aris.greekcitizenshipquiz.domain.model.QuestionOption
import com.aris.greekcitizenshipquiz.domain.usecase.AddIncorrectAnswerUseCase
import com.aris.greekcitizenshipquiz.domain.usecase.GetQuestionsByCategoryAndTypeUseCase
import com.aris.greekcitizenshipquiz.domain.usecase.GetQuestionsByCategoryUseCase
import com.aris.greekcitizenshipquiz.domain.usecase.GetNewQuestionsByCategoryUseCase
import com.aris.greekcitizenshipquiz.domain.usecase.ObserveIncorrectQuestionsUseCase
import com.aris.greekcitizenshipquiz.domain.usecase.RemoveIncorrectAnswerUseCase
import com.aris.greekcitizenshipquiz.ui.model.QuestionSource
import com.aris.greekcitizenshipquiz.ui.state.QuestionsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.aris.greekcitizenshipquiz.ui.model.AnswerState
import kotlinx.coroutines.Job
@HiltViewModel
class QuestionsViewModel @Inject constructor(

    private val observeIncorrectQuestionsUseCase: ObserveIncorrectQuestionsUseCase,

    private val getQuestionsByNewUseCase: GetNewQuestionsByCategoryUseCase,

    private val getQuestionsByCategoryUseCase: GetQuestionsByCategoryUseCase,

    private val getQuestionsByCategoryAndTypeUseCase: GetQuestionsByCategoryAndTypeUseCase,

    private val addIncorrectAnswerUseCase: AddIncorrectAnswerUseCase,
    private val removeIncorrectAnswerUseCase: RemoveIncorrectAnswerUseCase

) : ViewModel() {

    private val _totalQuestions = MutableStateFlow(0)

    val totalQuestions = _totalQuestions.asStateFlow()

    private val _uiState =
        MutableStateFlow<QuestionsUiState>(
            QuestionsUiState.Loading
        )

    val uiState =
        _uiState.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)

    val currentIndex = _currentIndex.asStateFlow()

    private var currentSource: QuestionSource? = null
    private var loadJob: Job? = null

    private val _answerState = MutableStateFlow(
        AnswerState.NONE
    )

    val answerState = _answerState.asStateFlow()

    private val _selectedOptionId = MutableStateFlow<Int?>(null)


    val selectedOptionId = _selectedOptionId.asStateFlow()

    private val _score = MutableStateFlow(0)

    val score = _score.asStateFlow()

    private val _isFinished = MutableStateFlow(false)

    val isFinished = _isFinished.asStateFlow()

    fun nextQuestion() {

        if (_currentIndex.value < _totalQuestions.value - 1) {
            _currentIndex.value++
            _answerState.value = AnswerState.NONE
            _selectedOptionId.value = null
        }else {

            _isFinished.value = true
        }

    }

    fun checkAnswer(
        option: QuestionOption,
        questionId: Int
    ) {

        if (_selectedOptionId.value != null) {
            return
        }

        _selectedOptionId.value = option.optionId


        _answerState.value =
            if (option.isCorrect) {

                _score.value = _score.value + 1
                AnswerState.CORRECT

            } else {

                AnswerState.INCORRECT
            }


        viewModelScope.launch {

            if (option.isCorrect) {

                if (currentSource?.isIncorrectMode == true) {

                    removeIncorrectAnswerUseCase(
                        questionId
                    )
                }

            } else {

                addIncorrectAnswerUseCase(
                    questionId
                )
            }
        }
    }

    fun finishQuiz() {

        _isFinished.value = true

    }

    fun loadQuestions(
        source: QuestionSource
    ) {

        loadJob?.cancel()

        currentSource = source

        _currentIndex.value = 0
        _totalQuestions.value = 0
        _answerState.value = AnswerState.NONE
        _selectedOptionId.value = null
        _score.value = 0
        _isFinished.value = false


        loadJob = viewModelScope.launch {

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

                _totalQuestions.value = questions.size

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
    override fun onCleared() {
        loadJob?.cancel()
        super.onCleared()
    }
}