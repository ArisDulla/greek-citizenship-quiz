package com.aris.greekcitizenshipquiz.ui.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.aris.greekcitizenshipquiz.ui.state.QuizUiState
import com.aris.greekcitizenshipquiz.ui.viewmodel.QuizViewModel
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding

@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
    ) {

        when (val currentState = state) {

            QuizUiState.Idle -> {

                Button(
                    onClick = {
                        viewModel.syncQuizData()
                    }
                ) {
                    Text(
                        text = "Συγχρονισμός δεδομένων"
                    )
                }
            }


            QuizUiState.Loading -> {

                Text(
                    text = "Γίνεται συγχρονισμός..."
                )
            }


            QuizUiState.Success -> {

                Text(
                    text = "Τα δεδομένα ενημερώθηκαν!"
                )
            }
            QuizUiState.NoUpdates -> {

                Text(
                    text = "Δεν υπάρχουν διαθέσιμες ενημερώσεις."
                )
            }

            is QuizUiState.Error -> {

                Text(
                    text = currentState.message
                )
            }
        }
    }
}