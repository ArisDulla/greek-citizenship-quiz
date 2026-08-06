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

private const val INCORRECT_QUESTIONS_SCREEN = "incorrect_questions"
private const val NEW_QUESTIONS_SCREEN = "questions_new"
private const val RANDOM_TEST_SCREEN = "random_test"

private fun questionsRoute(
    categoryId: Int,
    typeQuestionId: Int
) = "questions/$categoryId/$typeQuestionId"

private fun categoryTypesRoute(
    categoryId: Int
) = "$CATEGORY_TYPES_ROUTE/$categoryId"

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    fun openMenu() {
        navController.navigate(QUIZ_MENU_SCREEN) {
            popUpTo(QUIZ_MENU_SCREEN) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    NavHost(
        navController = navController,
        startDestination = HOME_SCREEN
    ) {

        //
        // HOME SCREEN
        //
        composable(
            route = HOME_SCREEN
        ) {

            QuizScreen(
                onOpenMenu = {
                    navController.navigate(QUIZ_MENU_SCREEN) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = QUESTIONS_SCREEN
        ) { backStackEntry ->


            val categoryId = requireNotNull(
                backStackEntry.arguments?.getString("categoryId")
            ).toInt()


            val typeQuestionId = requireNotNull(
                backStackEntry.arguments?.getString("typeQuestionId")
            ).toInt()


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

                    },

                onIncorrectAnswers = {

                    navController.navigate(INCORRECT_QUESTIONS_SCREEN) {
                        popUpTo(QUIZ_MENU_SCREEN) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }

                },

                onBackTo = {
                    navController.popBackStack()
                }

            )
        }

        composable(
            route = CATEGORY_TYPES_SCREEN
        ) { backStackEntry ->

            val categoryId = requireNotNull(
                backStackEntry.arguments?.getString("categoryId")
            ).toInt()

            CategoryTypesScreen(
                categoryId = categoryId,

                onOpenQuestions = { source ->

                    when(source) {

                        is QuestionSource.CategoryType -> {

                            //
                            // Questions Types
                            //
                            navController.navigate(
                                questionsRoute(
                                    source.categoryId,
                                    source.typeQuestionId
                                )
                            )

                        }

                        is QuestionSource.Category -> {

                            navController.navigate(
                                questionsRoute(
                                    source.categoryId,
                                    0
                                )
                            )

                        }

                        else -> {}
                    }
                },

                onMenu = { openMenu() }

            )
        }

        composable(
            route = INCORRECT_QUESTIONS_SCREEN
        ) {

            QuestionsScreen(
                source = QuestionSource.Incorrect,

                onIncorrectAnswers = {
                    // Not available in incorrect mode
                },

                onBackTo = {
                    navController.popBackStack()
                }
            )
        }

        //
        // Quiz Menu Screen
        //
        composable(
            route = QUIZ_MENU_SCREEN
        ) {

            QuizMenuScreen(

                //
                // ΗΟΜΕ
                //
                onHome = {
                navController.popBackStack(
                    HOME_SCREEN,
                    false
                )
                },

                //
                // Incorrect Questions
                //
                onIncorrectAnswers = {
                    navController.navigate(
                        INCORRECT_QUESTIONS_SCREEN
                    ){

                        launchSingleTop = true
                    }
                },

                //
                // Category Questions
                //
                onCategoryClick = { categoryId ->
                    navController.navigate(
                        categoryTypesRoute(categoryId)
                    ){
                        launchSingleTop = true
                    }
                },
                onNewQuestions = {
                    navController.navigate(
                        "questions_new"
                    )
                },
                onRandomTest = {
                    navController.navigate(RANDOM_TEST_SCREEN)
                }
            )
        }
        composable(
            route = RANDOM_TEST_SCREEN
        ) {

            QuestionsScreen(

                source = QuestionSource.RandomTest,

                onIncorrectAnswers = {

                    navController.navigate(INCORRECT_QUESTIONS_SCREEN) {
                        popUpTo(QUIZ_MENU_SCREEN) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }

                },

                onBackTo = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = NEW_QUESTIONS_SCREEN
        ) {

            QuestionsScreen(

                source = QuestionSource.NewQuestions,

                onIncorrectAnswers = {

                    navController.navigate(INCORRECT_QUESTIONS_SCREEN) {
                        popUpTo(QUIZ_MENU_SCREEN) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }

                },

                onBackTo = {
                    navController.popBackStack()
                }
            )
        }
    }
}
