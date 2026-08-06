package com.aris.greekcitizenshipquiz.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aris.greekcitizenshipquiz.ui.viewmodel.QuizMenuViewModel
import com.aris.greekcitizenshipquiz.ui.components.AppBackground
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.CircularProgressIndicator

@Composable
fun QuizMenuScreen(
    onHome: () -> Unit,
    onIncorrectAnswers: () -> Unit,
    onCategoryClick: (Int) -> Unit,
    viewModel: QuizMenuViewModel = hiltViewModel(),
    onNewQuestions: () -> Unit,
    onRandomTest: () -> Unit,
) {
    val incorrectCount by viewModel.incorrectCount.collectAsStateWithLifecycle()
    val categories by viewModel.categories.collectAsStateWithLifecycle()


    AppBackground {
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
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        onClick = onHome
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Αρχική",
                            fontSize = 20.sp
                        )
                    }

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
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Τα λάθη μου ($incorrectCount)",
                            fontSize = 20.sp
                        )
                    }

                }
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.70f),
                        contentColor = Color.DarkGray
                    ),
                    border = BorderStroke(
                        1.dp,
                        Color.White.copy(alpha = 0.25f)
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1565C0)
                            ),
                            onClick = onRandomTest
                        ) {

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "📝 Τεστ Εξέτασης\n20 Τυχαίες Ερωτήσεις",
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                lineHeight = 34.sp
                            )
                        }

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2E7D32)
                            ),
                            onClick = onNewQuestions
                        ) {
                            Text(
                                text = "Νέες ερωτήσεις",
                                fontSize = 20.sp,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                        if (categories.isEmpty()) {

                            CircularProgressIndicator(modifier = Modifier.padding(32.dp))

                        } else {

                            categories.forEach { category ->

                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(64.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primary
                                    ),
                                    onClick = {
                                        onCategoryClick(category.categoryId)
                                    }
                                ) {
                                    Text(
                                        text = category.description ?: "",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        fontSize = 20.sp
                                    )
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}
