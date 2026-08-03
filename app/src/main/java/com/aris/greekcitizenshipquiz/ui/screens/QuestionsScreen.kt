package com.aris.greekcitizenshipquiz.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aris.greekcitizenshipquiz.ui.components.QuestionItem
import com.aris.greekcitizenshipquiz.ui.model.QuestionSource
import com.aris.greekcitizenshipquiz.ui.state.QuestionsUiState
import com.aris.greekcitizenshipquiz.ui.viewmodel.QuestionsViewModel
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import com.aris.greekcitizenshipquiz.ui.components.AppBackground

@Composable
fun QuestionsScreen(
    source: QuestionSource,
    viewModel: QuestionsViewModel = hiltViewModel()
) {

    val currentIndex by viewModel.currentIndex
        .collectAsStateWithLifecycle()

    val uiState by viewModel.uiState
        .collectAsStateWithLifecycle()

    LaunchedEffect(source) {

        viewModel.loadQuestions(source)

    }
    AppBackground {

        when (val state = uiState) {

            is QuestionsUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is QuestionsUiState.Success -> {
                if (state.questions.isNotEmpty()) {

                    val question = state.questions.getOrNull(currentIndex)


                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),

                        contentPadding = PaddingValues(
                            top = 70.dp,
                            bottom = 70.dp
                        ),

                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {

                            Text(
                                text = "${currentIndex + 1} / ${state.questions.size}"
                            )
                        }

                        item {
                            question?.let {

                                QuestionItem(
                                    question = it
                                )

                            }
                        }

                        item {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp),

                                onClick = {

                                    viewModel.nextQuestion()

                                }

                            ) {

                                Text(
                                    text =
                                        if (currentIndex == state.questions.lastIndex)
                                            "Τέλος"
                                        else
                                            "Επόμενη"
                                )

                            }
                        }
                    }
                }
            }

            is QuestionsUiState.Empty -> {


                Box(
                    modifier = Modifier
                        .fillMaxSize(),

                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = state.message
                    )

                }
            }

            is QuestionsUiState.Error -> {


                Box(
                    modifier = Modifier
                        .fillMaxSize(),

                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = state.message
                    )

                }
            }
        }
    }
}