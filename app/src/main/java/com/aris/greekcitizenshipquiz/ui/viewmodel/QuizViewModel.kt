package com.aris.greekcitizenshipquiz.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aris.greekcitizenshipquiz.data.remote.error.SyncException
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

                    val error =
                        result.exceptionOrNull()

                    when (error) {

                        is SyncException -> {

                            when(error.code) {

                                400 ->
                                    QuizUiState.Error(
                                        "Μη έγκυρη ενημέρωση"
                                    )

                                404 ->
                                    QuizUiState.Error(
                                        "Δεν βρέθηκαν δεδομένα ενημέρωσης"
                                    )

                                500 ->
                                    QuizUiState.Error(
                                        "Πρόβλημα διακομιστή"
                                    )

                                else ->
                                    QuizUiState.Error(
                                        "Άγνωστο σφάλμα"
                                    )
                            }
                        }

                        else ->
                            QuizUiState.Error(
                                "Πρόβλημα σύνδεσης"
                            )
                    }
                }
        }
    }

}