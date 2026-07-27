package com.aris.greekcitizenshipquiz.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aris.greekcitizenshipquiz.R
import com.aris.greekcitizenshipquiz.ui.state.QuizUiState
import com.aris.greekcitizenshipquiz.ui.viewmodel.QuizViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import com.aris.greekcitizenshipquiz.ui.components.SyncButtonContent
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.text.style.TextAlign

@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    onOpenMenu: () -> Unit,
    viewModel: QuizViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val examTitle by viewModel.examPeriodTitle.collectAsStateWithLifecycle()

    val isLoading = state == QuizUiState.Loading

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        // Background image
        Image(
            painter = painterResource(R.drawable.splash_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x88003366),
                            Color(0x66000000),
                            Color(0x33000000)
                        )
                    )
                )
        )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 32.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(
                    modifier = Modifier.height(60.dp)
                )


                Text(
                    text = "🇬🇷 Ελληνική Ιθαγένεια",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )


                Spacer(
                    modifier = Modifier.height(8.dp)
                )





                Spacer(
                    modifier = Modifier.weight(1f)
                )


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.20f),
                        contentColor = Color.DarkGray
                    ),
                    border = BorderStroke(
                        1.dp,
                        Color.White.copy(alpha = 0.25f)
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {


                        Button(
                            modifier = Modifier
                                .width(240.dp)
                                .height(56.dp),
                            enabled = !isLoading,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = when (state) {

                                    QuizUiState.Success -> Color(0xFF2E7D32)

                                    QuizUiState.NoUpdates -> MaterialTheme.colorScheme.primary

                                    is QuizUiState.Error -> Color(0xFFC62828)

                                    else -> MaterialTheme.colorScheme.primary
                                }
                            ),
                            onClick = {
                                viewModel.syncQuizData()
                            }
                        ) {
                            SyncButtonContent(
                                state = state
                            )
                        }

                        Button(
                            enabled = examTitle != null,
                            modifier = Modifier
                                .width(240.dp)
                                .height(56.dp),
                            onClick = {
                                onOpenMenu()
                            }
                        ) {

                            Icon(
                                imageVector = Icons.Default.School,
                                contentDescription = null
                            )

                            Spacer(
                                modifier = Modifier.width(8.dp)
                            )

                            Text(
                                examTitle ?: "Φόρτωση..."
                            )
                        }
                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )
                        Text(
                            text = "Εκπαιδευτική εφαρμογή προετοιμασίας.\n" +
                                    "Δεν αποτελεί επίσημη υπηρεσία του Ελληνικού Δημοσίου.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Black.copy(alpha = 0.75f),
                            textAlign = TextAlign.Center
                        )

                    }
                }
                Spacer(
                    modifier = Modifier.height(32.dp)
                )
        }
    }
}