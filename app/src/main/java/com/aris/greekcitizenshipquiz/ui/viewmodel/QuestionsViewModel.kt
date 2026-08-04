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
import com.aris.greekcitizenshipquiz.domain.model.Question
import com.aris.greekcitizenshipquiz.data.util.AnswerNormalizer

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

    private var questions: List<Question> = emptyList()

    private val currentQuestion: Question?
        get() = questions.getOrNull(_currentIndex.value)

    private val _answerState = MutableStateFlow(
        AnswerState.NONE
    )

    val answerState = _answerState.asStateFlow()

    private val _selectedOptionId = MutableStateFlow<Int?>(null)


    val selectedOptionId = _selectedOptionId.asStateFlow()

    private val _textAnswers = MutableStateFlow<List<String>>(emptyList())

    val textAnswers = _textAnswers.asStateFlow()

    private val _textAnswerResults =
        MutableStateFlow<List<Boolean?>>(emptyList())

    val textAnswerResults =
        _textAnswerResults.asStateFlow()

    private val _score = MutableStateFlow(0)

    val score = _score.asStateFlow()

    private val _isFinished = MutableStateFlow(false)

    val isFinished = _isFinished.asStateFlow()

    fun nextQuestion() {

        if (_currentIndex.value < _totalQuestions.value - 1) {
            _currentIndex.value++
            _answerState.value = AnswerState.NONE
            _selectedOptionId.value = null

            _textAnswers.value = List(
                (currentQuestion?.maxCorrect ?: 0).coerceAtLeast(1)
            ) { "" }


            _textAnswerResults.value =
                List(
                    (currentQuestion?.maxCorrect ?: 0).coerceAtLeast(1)
                ) { null }
        }else {

            _isFinished.value = true
        }

    }
    fun checkTextAnswers(question: Question) {

        val remainingAnswers = question.options
            .filter { it.isCorrect }
            .mapNotNull { it.optionText }
            .map { AnswerNormalizer.normalize(it) }
            .toMutableList()

        val results = _textAnswers.value.map { answer ->

            val normalized = AnswerNormalizer.normalize(answer)

            if (remainingAnswers.contains(normalized)) {
                remainingAnswers.remove(normalized) // χρησιμοποιήθηκε ήδη
                true
            } else {
                false
            }
        }
        _textAnswers.value = _textAnswers.value.mapIndexed { index, answer ->
            if (results.getOrNull(index) == false) {
                ""
            } else {
                answer
            }
        }

        _textAnswerResults.value = results

        val isCorrect =
            results.isNotEmpty() &&
                    results.all { it }

        viewModelScope.launch {


            if (isCorrect) {

                if (currentSource?.isIncorrectMode == true) {
                    removeIncorrectAnswerUseCase(question.questionId)
                }

            } else {

                addIncorrectAnswerUseCase(question.questionId)
            }
        }

        if (isCorrect) {
            _score.value++
            _answerState.value = AnswerState.CORRECT
        } else {
            _answerState.value = AnswerState.INCORRECT
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
        _textAnswers.value = emptyList()
        _textAnswerResults.value = emptyList()

        this@QuestionsViewModel.questions = emptyList()


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
                this@QuestionsViewModel.questions = questions

                _totalQuestions.value = questions.size

                _textAnswers.value =
                    List(
                        (questions.firstOrNull()?.maxCorrect ?: 0).coerceAtLeast(1)
                    ) { "" }
                _textAnswerResults.value =
                    List(
                        (questions.firstOrNull()?.maxCorrect ?: 0).coerceAtLeast(1)
                    ) { null }

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

    fun updateTextAnswer(
        index: Int,
        value: String
    ) {

        val current = _textAnswers.value.toMutableList()

        current[index] = value

        _textAnswers.value = current
    }
    override fun onCleared() {
        loadJob?.cancel()
        super.onCleared()
    }
}