package com.aris.greekcitizenshipquiz.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aris.greekcitizenshipquiz.ui.model.QuestionSource
import com.aris.greekcitizenshipquiz.ui.screens.CategoryTypesScreen
import com.aris.greekcitizenshipquiz.ui.screens.QuestionsScreen
import com.aris.greekcitizenshipquiz.ui.screens.QuizMenuScreen
import com.aris.greekcitizenshipquiz.ui.screens.QuizScreen

private const val HOME_SCREEN = "quiz_screen"
private const val QUIZ_MENU_SCREEN = "quiz_menu_screen"
private const val CATEGORY_TYPES_ROUTE = "category_types"
private const val CATEGORY_TYPES_SCREEN = "$CATEGORY_TYPES_ROUTE/{categoryId}"

private const val QUESTIONS_SCREEN = "questions/{categoryId}/{typeQuestionId}"

@Composable
fun NavGraph() {

    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = HOME_SCREEN
    ) {


        composable(
            route = HOME_SCREEN
        ) {

            QuizScreen(
                onOpenMenu = {
                    navController.navigate(QUIZ_MENU_SCREEN)
                }
            )
        }
        composable(
            route = QUESTIONS_SCREEN
        ) { backStackEntry ->


            val categoryId =
                backStackEntry.arguments
                    ?.getString("categoryId")
                    ?.toInt() ?: 0


            val typeQuestionId =
                backStackEntry.arguments
                    ?.getString("typeQuestionId")
                    ?.toInt() ?: 0



            QuestionsScreen(

                source =
                    if (typeQuestionId != 0) {

                        QuestionSource.CategoryType(
                            categoryId = categoryId,
                            typeQuestionId = typeQuestionId
                        )

                    } else {

                        QuestionSource.Category(
                            categoryId = categoryId
                        )

                    }

            )
        }

        composable(
            route = CATEGORY_TYPES_SCREEN
        ) { backStackEntry ->

            val categoryId =
                backStackEntry.arguments
                    ?.getString("categoryId")
                    ?.toInt() ?: 0

            CategoryTypesScreen(
                categoryId = categoryId,

                onOpenQuestions = { source ->

                    when(source) {

                        is QuestionSource.CategoryType -> {

                            navController.navigate(
                                "questions/${source.categoryId}/${source.typeQuestionId}"
                            )

                        }

                        is QuestionSource.Category -> {

                            navController.navigate(
                                "questions/${source.categoryId}/0"
                            )

                        }

                        else -> {}
                    }
                },

                onMenu = {
                    navController.navigate(QUIZ_MENU_SCREEN) {

                        popUpTo(QUIZ_MENU_SCREEN) {
                            inclusive = false
                        }

                        launchSingleTop = true
                    }
                }

            )
        }


        composable(
            route = QUIZ_MENU_SCREEN
        ) {

            QuizMenuScreen(
                onHome = {
                    navController.navigate(HOME_SCREEN) {

                        popUpTo(HOME_SCREEN) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                },

                onIncorrectAnswers = {
                    // navController.navigate(INCORRECT_ANSWERS_SCREEN)
                },

                onCategoryClick = { categoryId ->
                    navController.navigate("$CATEGORY_TYPES_ROUTE/$categoryId")
                }
            )
        }
    }
}