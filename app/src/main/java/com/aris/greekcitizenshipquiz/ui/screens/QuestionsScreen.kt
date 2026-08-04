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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp
import com.aris.greekcitizenshipquiz.ui.components.AppBackground
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline

@Composable
fun QuestionsScreen(
    source: QuestionSource,
    viewModel: QuestionsViewModel = hiltViewModel(),
    onIncorrectAnswers: () -> Unit,
    onMenu: () -> Unit,
) {

    val currentIndex by viewModel.currentIndex
        .collectAsStateWithLifecycle()

    val uiState by viewModel.uiState
        .collectAsStateWithLifecycle()

    val answerState by viewModel.answerState.collectAsStateWithLifecycle()

    val selectedOptionId by viewModel.selectedOptionId
        .collectAsStateWithLifecycle()


    val isFinished by viewModel.isFinished.collectAsStateWithLifecycle()

    val totalQuestions by viewModel.totalQuestions
        .collectAsStateWithLifecycle()

    val score by viewModel.score.collectAsStateWithLifecycle()

    LaunchedEffect(source) {

        viewModel.loadQuestions(source)

    }
    AppBackground {

        if (isFinished) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Σκορ: $score / $totalQuestions",
                    fontSize = 22.sp
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),

                    onClick = {
                        viewModel.loadQuestions(source)
                    }
                ) {

                    Text(
                        text = "Ξανά προσπάθεια",
                        fontSize = 20.sp
                    )
                }


                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                if (!source.isIncorrectMode ) {

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE65100)
                        ),

                        onClick = onIncorrectAnswers
                    ) {

                        Icon(
                            imageVector = Icons.Default.ErrorOutline,
                            contentDescription = null
                        )

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        Text(
                            text = "Τα λάθη μου",
                            fontSize = 20.sp
                        )
                    }
                }
                Spacer(
                    modifier = Modifier.height(12.dp)
                )


                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),

                    onClick = onMenu
                ) {

                    Text(
                        text = "Μενού",
                        fontSize = 20.sp
                    )
                }
            }

        } else {

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
                                        question = it,
                                        answerState = answerState,
                                        selectedOptionId = selectedOptionId,
                                        onOptionSelected = { option ->
                                            viewModel.checkAnswer(
                                                option = option,
                                                questionId = it.questionId
                                            )

                                        }
                                    )

                                }
                            }

                            item {
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp),

                                    onClick = {

                                        if (currentIndex == state.questions.lastIndex) {

                                            viewModel.finishQuiz()

                                        } else {

                                            viewModel.nextQuestion()

                                        }

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
}