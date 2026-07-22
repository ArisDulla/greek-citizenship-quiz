package com.aris.greekcitizenshipquiz.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aris.greekcitizenshipquiz.ui.state.QuizUiState
import com.aris.greekcitizenshipquiz.ui.viewmodel.QuizViewModel
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.MaterialTheme

@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val examTitle by viewModel.examPeriodTitle.collectAsStateWithLifecycle()


    val isLoading = state == QuizUiState.Loading


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Πάνω κουμπί ενημέρωσης

        Spacer(
            modifier = Modifier.height(40.dp)
        )


        Button(
            modifier = Modifier
                .width(220.dp)
                .heightIn(min = 50.dp),
            enabled = !isLoading,
            onClick = {
                viewModel.syncQuizData()
            }
        ) {

            if (isLoading) {

                CircularProgressIndicator(
                    modifier = Modifier.size(22.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )

            } else {

                Text(
                    text = "Λήψη ενημέρωσης"
                )
            }
        }


        Spacer(
            modifier = Modifier.height(16.dp)
        )


        // Σταθερός χώρος μηνυμάτων

        Column(
            modifier = Modifier.heightIn(min = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            when (val currentState = state) {

                QuizUiState.Idle -> Unit

                QuizUiState.Loading -> {

                    Text(
                        text = "Κατέβασμα δεδομένων..."
                    )
                }

                QuizUiState.Success -> {

                    Text(
                        text = "Η ενημέρωση ολοκληρώθηκε!"
                    )
                }

                QuizUiState.NoUpdates -> {

                    Text(
                        text = "Δεν υπάρχουν νέες ενημερώσεις."
                    )
                }

                is QuizUiState.Error -> {

                    Text(
                        text = currentState.message
                    )
                }
            }
        }


        // Σπρώχνει το επόμενο κουμπί στο κέντρο

        Spacer(
            modifier = Modifier.weight(1f)
        )


        // ΚΟΥΜΠΙ ΠΕΡΙΟΔΟΥ ΕΞΕΤΑΣΗΣ

        Button(
            enabled = examTitle != null,
            modifier = Modifier
                .width(260.dp)
                .height(55.dp),
            onClick = {

            }
        ) {

            Text(
                text = examTitle ?: "Φόρτωση..."
            )
        }


        Spacer(
            modifier = Modifier.weight(1f)
        )
    }
}