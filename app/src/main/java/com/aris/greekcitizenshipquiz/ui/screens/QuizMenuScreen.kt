package com.aris.greekcitizenshipquiz.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aris.greekcitizenshipquiz.ui.viewmodel.QuizMenuViewModel

@Composable
fun QuizMenuScreen(
    onBack: () -> Unit,
    onHome: () -> Unit,
    onIncorrectAnswers: () -> Unit,
    viewModel: QuizMenuViewModel = hiltViewModel()
) {
    val incorrectCount by viewModel.incorrectCount.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onHome
        ) {
            Text(
                text = "Αρχική"
            )
        }


        Spacer(
            modifier = Modifier.height(16.dp)
        )


        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onIncorrectAnswers
        ) {
            Text(
                text = "Τα λάθη μου ($incorrectCount)"
            )
        }
    }
}