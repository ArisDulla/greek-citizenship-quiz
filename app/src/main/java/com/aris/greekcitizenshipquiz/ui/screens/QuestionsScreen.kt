package com.aris.greekcitizenshipquiz.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.ui.text.style.TextAlign
import com.aris.greekcitizenshipquiz.ui.model.AnswerState

@Composable
fun QuestionsScreen(
    source: QuestionSource,
    viewModel: QuestionsViewModel = hiltViewModel(),
    onIncorrectAnswers: () -> Unit,
    onBackTo: () -> Unit
) {

    val currentIndex by viewModel.currentIndex
        .collectAsStateWithLifecycle()

    val uiState by viewModel.uiState
        .collectAsStateWithLifecycle()

    val answerState by viewModel.answerState.collectAsStateWithLifecycle()

    val selectedOptionId by viewModel.selectedOptionId
        .collectAsStateWithLifecycle()

    val textAnswers by viewModel.textAnswers.collectAsStateWithLifecycle()

    val textAnswerResults by viewModel.textAnswerResults
        .collectAsStateWithLifecycle()

    val isFinished by viewModel.isFinished.collectAsStateWithLifecycle()

    val totalQuestions by viewModel.totalQuestions
        .collectAsStateWithLifecycle()

    val score by viewModel.score.collectAsStateWithLifecycle()

    val correctAnswers by viewModel.correctAnswers.collectAsStateWithLifecycle()

    LaunchedEffect(source) {

        viewModel.loadQuestions(source)

    }
    AppBackground {

        if (isFinished) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.35f)
                    ),
                    border = BorderStroke(
                        1.dp,
                        Color.White.copy(alpha = 0.4f)
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Surface(
                            shape = RoundedCornerShape(50.dp),
                            color = Color.White.copy(alpha = 0.70f)
                        ) {

                            Text(
                                text = "Σκορ: $score / $totalQuestions",
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .padding(
                                        horizontal = 20.dp,
                                        vertical = 8.dp
                                    )
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF37474F)
                            ),


                            onClick = onBackTo
                        ) {

                            Text(
                                text = "Μενού",
                                fontSize = 20.sp
                            )
                        }
                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2E7D32)
                            ),

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


                        if (source.isIncorrectMode) {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(64.dp),

                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFE65100)
                                ),
                                onClick = {
                                    viewModel.loadQuestions(source)
                                }
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

                        } else {

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
                    }
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
                                bottom = 480.dp
                            ),

                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            item {

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {

                                    Button(
                                        modifier = Modifier
                                            .height(56.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFF37474F)
                                        ),
                                        onClick = onBackTo
                                    ) {

                                        Text(
                                            text = "Μενού",
                                            fontSize = 18.sp
                                        )
                                    }


                                    Surface(
                                        shape = RoundedCornerShape(50.dp),
                                        color = Color.White.copy(alpha = 0.70f)
                                    ) {

                                        Text(
                                            text = "${currentIndex + 1} / ${state.questions.size}",
                                            fontSize = 18.sp,
                                            modifier = Modifier
                                                .padding(
                                                    horizontal = 20.dp,
                                                    vertical = 8.dp
                                                )
                                        )
                                    }
                                }
                            }

                            item {
                                question?.let {

                                    QuestionItem(
                                        question = it,
                                        answerState = answerState,
                                        selectedOptionId = selectedOptionId,
                                        textAnswers = textAnswers,
                                        textAnswerResults = textAnswerResults,

                                        onTextAnswerChanged = { index, value ->
                                            viewModel.updateTextAnswer(
                                                index,
                                                value
                                            )
                                        },
                                        onCheckTextAnswers = {
                                            viewModel.checkTextAnswers(it)
                                        },
                                        onOptionSelected = { option ->
                                            viewModel.checkAnswer(
                                                option = option,
                                                questionId = it.questionId
                                            )

                                        },
                                        correctAnswers = correctAnswers
                                    )

                                }
                            }
                            if (answerState == AnswerState.CORRECT ||
                                answerState == AnswerState.INCORRECT) {
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
                }

                is QuestionsUiState.Empty -> {

                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        Card(
                            modifier = Modifier
                                .fillMaxWidth(0.85f),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White.copy(alpha = 0.85f)
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 8.dp
                            )
                        ) {

                            Column(
                                modifier = Modifier
                                    .padding(24.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {

                                Text(
                                    text = state.message,
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.Center,
                                    color = Color.DarkGray
                                )

                                Spacer(
                                    modifier = Modifier.height(24.dp)
                                )

                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF37474F)
                                    ),
                                    shape = RoundedCornerShape(16.dp),
                                    onClick = onBackTo
                                ) {

                                    Text(
                                        text = "Επιστροφή στο Μενού",
                                        fontSize = 18.sp
                                    )
                                }
                            }
                        }
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