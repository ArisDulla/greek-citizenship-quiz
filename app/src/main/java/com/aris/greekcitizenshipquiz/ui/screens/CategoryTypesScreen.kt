package com.aris.greekcitizenshipquiz.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.aris.greekcitizenshipquiz.ui.viewmodel.CategoryTypesViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import com.aris.greekcitizenshipquiz.ui.components.AppBackground
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aris.greekcitizenshipquiz.ui.components.AllQuestionsButton
import com.aris.greekcitizenshipquiz.ui.components.CategoryHeader
import com.aris.greekcitizenshipquiz.ui.state.TypesUiState
import com.aris.greekcitizenshipquiz.ui.model.QuestionSource
@Composable
fun CategoryTypesScreen(
    onMenu: () -> Unit,
    categoryId: Int,
    onOpenQuestions: (QuestionSource) -> Unit,
    viewModel: CategoryTypesViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(categoryId) {
        viewModel.loadTypes(categoryId)
    }
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

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF37474F)
                    ),
                    onClick = onMenu,
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Κατηγορίες",
                        fontSize = 20.sp
                    )
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.50f)
                    ),
                    border = BorderStroke(
                        1.dp,
                        Color.White.copy(alpha = 0.5f)
                    )
                ) {


                        AnimatedContent(
                            targetState = uiState,
                        ) { state ->

                            Column(
                                modifier = Modifier
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                            when (state) {
                                is TypesUiState.Loading -> {

                                    Spacer(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(120.dp)
                                    )
                                }

                                is TypesUiState.Success -> {


                                    CategoryHeader(
                                        category = state.data.category
                                    )

                                    AllQuestionsButton(
                                        onClick = {
                                            onOpenQuestions(
                                                QuestionSource.Category(
                                                    categoryId = categoryId
                                                )
                                            )
                                        }
                                    )

                                    state.data.types.forEach { type ->

                                        Button(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .defaultMinSize(minHeight = 64.dp),

                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color.White,
                                                contentColor = Color.DarkGray
                                            ),

                                            onClick = {
                                                onOpenQuestions(
                                                    QuestionSource.CategoryType(
                                                        categoryId = categoryId,
                                                        typeQuestionId= type.typeQuestionId
                                                    )
                                                )
                                            }

                                        ) {

                                            Text(
                                                text = "${type.description} (${type.count})",
                                                fontSize = 20.sp,
                                                lineHeight = 32.sp,
                                                modifier = Modifier.fillMaxWidth(),
                                                textAlign = TextAlign.Center
                                            )
                                        }

                                    }

                                }

                                is TypesUiState.Empty -> {

                                    Text(
                                        text = state.message,
                                        fontSize = 18.sp,
                                        color = Color.DarkGray,
                                        modifier = Modifier.padding(24.dp),
                                        textAlign = TextAlign.Center
                                    )
                                }

                                is TypesUiState.Error -> {

                                    Text(
                                        text = state.message,
                                        fontSize = 18.sp,
                                        color = Color.Red,
                                        modifier = Modifier.padding(24.dp),
                                        textAlign = TextAlign.Center
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