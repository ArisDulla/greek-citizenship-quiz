package com.aris.greekcitizenshipquiz.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aris.greekcitizenshipquiz.data.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SyncViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {


    private val _syncState =
        MutableStateFlow<SyncState>(SyncState.Idle)

    val syncState: StateFlow<SyncState> =
        _syncState


    fun sync() {

        Log.d("SYNC", "sync() called")


        viewModelScope.launch {

            _syncState.value =
                SyncState.Loading


            val result =
                quizRepository.syncQuizData()

            Log.d("SYNC", "result = $result")

            _syncState.value =
                if (result.isSuccess) {

                    SyncState.Success

                } else {

                    SyncState.Error(
                        result.exceptionOrNull()
                            ?.message ?: "Unknown error"
                    )
                }
        }
    }
}


sealed class SyncState {

    data object Idle : SyncState()

    data object Loading : SyncState()

    data object Success : SyncState()

    data class Error(
        val message: String
    ) : SyncState()
}